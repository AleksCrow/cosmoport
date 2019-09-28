package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/ships", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipRestController {

    @Autowired
    private ShipService shipService;

    @GetMapping
    public @ResponseBody List<Ship> getAllShips(@RequestParam(value = "order", defaultValue = "ID") String order,
                                                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize) {

        return shipService.getAllByName(pageNumber, pageSize, ShipOrder.valueOf(order).getFieldName());
    }

    @GetMapping("/count")
    public int getCount() {
        return shipService.getCount();
    }
}
