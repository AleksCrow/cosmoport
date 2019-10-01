package com.space.service;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ShipService {

    List<Ship> getAll();

    List<Ship> getAll(int pageNumber, int pageSize, String sort, Specification<Ship> spec);

    Optional<Ship> findById(Long id);

    Ship create(Ship ship);

    void delete(Long id);

    Integer getCount();
}
