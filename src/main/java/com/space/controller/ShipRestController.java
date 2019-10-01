package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.util.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.space.util.ShipFilterSpecification.shipNameOrPlanetContainsIgnoreCase;

@RestController
@RequestMapping(value = "/rest/ships", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipRestController {

    @Autowired
    private ShipService shipService;

    @GetMapping
    public @ResponseBody List<Ship> getAllShips(@RequestParam(value = "order", defaultValue = "ID") String order,
                                                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                                                @RequestParam(required = false, value = "name", defaultValue = "") String name,
                                                @RequestParam(required = false, value = "planet", defaultValue = "") String planet,
                                                @RequestParam(required = false, value = "shipType", defaultValue = "") ShipType shipType) {

        Specification<Ship> spec = Specification.where(shipNameOrPlanetContainsIgnoreCase(name)).and(shipNameOrPlanetContainsIgnoreCase(planet));
        return shipService.getAll(pageNumber, pageSize, ShipOrder.valueOf(order).getFieldName(), spec);
    }

//    @GetMapping("/")
//    public @ResponseBody List<Ship> findByNameAndPlanet(@RequestParam(required = false, value = "name", defaultValue = "") String name,
//                                                        @RequestParam(required = false, value = "planet", defaultValue = "") String planet) {
//        return SearchAndFiltering.SearchAndFilteringByShipNameAndPlanet(
//                SearchAndFiltering.SearchAndFilteringByShipName(shipService.getAll(), name),
//                SearchAndFiltering.SearchAndFilteringByPlanet(shipService.getAll(), planet));
//    }

    @GetMapping("/count")
    public int getCount() {
        return shipService.getCount();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Ship> findById(@PathVariable Long id) {
        Optional<Ship> shipOptional = shipService.findById(id);

        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(shipOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable Long id) {

        Optional<Ship> shipOptional = shipService.findById(id);
        if (id < 1){
            return ResponseEntity.badRequest().build();
        }
        if (!shipOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        this.shipService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> create(@Valid @RequestBody Ship ship, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        System.out.println(ship.getProdDate().toLocalDate());
        ship.setRating(Rating.ratingCalc(ship.getUsed(), ship.getSpeed(), ship.getProdDate()));

        return new ResponseEntity<>(shipService.create(ship), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Ship> update(@Valid @RequestBody Ship ship, BindingResult result, @PathVariable Long id) {

        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ship.setId(id);

        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        ship.setRating(Rating.ratingCalc(ship.getUsed(), ship.getSpeed(), ship.getProdDate()));
        return new ResponseEntity<>(shipService.create(ship), HttpStatus.OK);
    }
}
