package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.util.Rating;
import com.space.util.ShipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.space.util.ShipFilterSpecification.*;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipRestController {

    @Autowired
    private ShipService shipService;

    @GetMapping("/ships")
    public @ResponseBody List<Ship> getAllShips(@RequestParam(value = "order", defaultValue = "ID") String order,
                                                @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                                                @RequestParam(required = false, value = "name", defaultValue = "") String name,
                                                @RequestParam(required = false, value = "planet", defaultValue = "") String planet,
                                                @RequestParam(required = false, value = "shipType", defaultValue = "") ShipType shipType,
                                                @RequestParam(required = false, value = "after", defaultValue = "26192302747154") Long minProdDate,
                                                @RequestParam(required = false, value = "before", defaultValue = "33134715547154") Long maxProdDate,
                                                @RequestParam(required = false, value = "minCrewSize", defaultValue = "1") Integer minCrewSize,
                                                @RequestParam(required = false, value = "maxCrewSize", defaultValue = "9999") Integer maxCrewSize,
                                                @RequestParam(required = false, value = "minSpeed", defaultValue = "0.01") Double minSpeed,
                                                @RequestParam(required = false, value = "maxSpeed", defaultValue = "0.99") Double maxSpeed,
                                                @RequestParam(required = false, value = "minRating", defaultValue = "0.01") Double minRating,
                                                @RequestParam(required = false, value = "maxRating", defaultValue = "10") Double maxRating,
                                                @RequestParam(required = false, value = "isUsed", defaultValue = "") Boolean isUsed) {

        //спецификация для поиска и фильтрации
        Specification<Ship> spec = Specification.where(shipNameContainsIgnoreCase(name))
                                                        .and(shipPlanetContainsIgnoreCase(planet))
                                                        .and(shipTypeSpecification(shipType))
                                                        .and(prodDateRange(minProdDate, maxProdDate))
                                                        .and(crewSizeRange(minCrewSize, maxCrewSize, "crewSize"))
                                                        .and(SpeedAndRatingRange(minSpeed, maxSpeed, "speed"))
                                                        .and(SpeedAndRatingRange(minRating, maxRating, "rating"))
                                                        .and(isUsed(isUsed));



        return shipService.getAll(pageNumber, pageSize, ShipOrder.valueOf(order).getFieldName(), spec);
    }

    @GetMapping("/ships/count")
    public int getCount(@RequestParam(required = false, value = "name", defaultValue = "") String name,
                        @RequestParam(required = false, value = "planet", defaultValue = "") String planet,
                        @RequestParam(required = false, value = "shipType", defaultValue = "") ShipType shipType,
                        @RequestParam(required = false, value = "after", defaultValue = "26192302747154") Long minProdDate,
                        @RequestParam(required = false, value = "before", defaultValue = "33134715547154") Long maxProdDate,
                        @RequestParam(required = false, value = "minCrewSize", defaultValue = "1") Integer minCrewSize,
                        @RequestParam(required = false, value = "maxCrewSize", defaultValue = "9999") Integer maxCrewSize,
                        @RequestParam(required = false, value = "minSpeed", defaultValue = "0.01") Double minSpeed,
                        @RequestParam(required = false, value = "maxSpeed", defaultValue = "0.99") Double maxSpeed,
                        @RequestParam(required = false, value = "minRating", defaultValue = "0.01") Double minRating,
                        @RequestParam(required = false, value = "maxRating", defaultValue = "10") Double maxRating,
                        @RequestParam(required = false, value = "isUsed", defaultValue = "") Boolean isUsed) {

        Specification<Ship> spec = Specification.where(shipNameContainsIgnoreCase(name))
                .and(shipPlanetContainsIgnoreCase(planet))
                .and(shipTypeSpecification(shipType))
                .and(prodDateRange(minProdDate, maxProdDate))
                .and(crewSizeRange(minCrewSize, maxCrewSize, "crewSize"))
                .and(SpeedAndRatingRange(minSpeed, maxSpeed, "speed"))
                .and(SpeedAndRatingRange(minRating, maxRating, "rating"))
                .and(isUsed(isUsed));

        return shipService.getAll(spec).size();
    }

    @GetMapping(value = "/ships/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Ship> findById(@PathVariable Long id) {
        Optional<Ship> ship = shipService.findById(id);

        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!ship.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ships/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Ship> ship = shipService.findById(id);
        if (id < 1){
            return ResponseEntity.badRequest().build();
        }
        if (!ship.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        this.shipService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/ships", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> create(@RequestBody Ship ship) {

        if (ShipValidator.validShipFields(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ship.setRating(Rating.ratingCalc(ship.getUsed(), ship.getSpeed(), ship.getProdDate()));

        return new ResponseEntity<>(shipService.save(ship), HttpStatus.OK);
    }

    @PostMapping(value = "/ships/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ship> update(@RequestBody Ship ship, @PathVariable("id") Long id) {

        if (id < 1 || ShipValidator.validShipFields(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!shipService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (ShipValidator.validShipNeedFields(ship)) {
            return new ResponseEntity<>(ship, HttpStatus.OK);
        }
        ship.setId(id);

        ship.setRating(Rating.ratingCalc(ship.getUsed(), ship.getSpeed(), ship.getProdDate()));
        return new ResponseEntity<>(shipService.update(ship), HttpStatus.OK);
    }
}
