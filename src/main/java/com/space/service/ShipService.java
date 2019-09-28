package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {

    List<Ship> getAll();

    Ship getById(Long id);

    void save(Ship ship);

    void delete(Long id);
}
