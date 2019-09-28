package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Optional;

public interface ShipService {

    List<Ship> getAll();

    List<Ship> getAllByName(int pageNumber, int pageSize, String sort);

    Optional<Ship> findById(Long id);

    Ship create(Ship ship);

    void delete(Long id);

    Integer getCount();
}
