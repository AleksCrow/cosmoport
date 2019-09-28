package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/ships")
public class ShipRestController {

    @Autowired
    private final ShipService shipService;

    public ShipRestController(ShipService shipService) {
        this.shipService = shipService;
    }

//    @GetMapping("/")
//    public String home() {
//        return "redirect:/rest/ships";
//    }

    @GetMapping
    public @ResponseBody List<Ship> getAllShips() {

        return shipService.getAll();
    }
}
