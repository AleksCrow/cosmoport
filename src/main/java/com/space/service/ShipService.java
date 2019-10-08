package com.space.service;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ShipService {

    List<Ship> getAll(Specification<Ship> spec);

    List<Ship> getAll(int pageNumber, int pageSize, String sort, Specification<Ship> spec);

    Optional<Ship> findById(Long id);

    Ship save(Ship ship);

    void delete(Long id);

    Ship update(Ship ship);
}
