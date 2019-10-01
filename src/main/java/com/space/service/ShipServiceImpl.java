package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository repository;

    @Override
    public List<Ship> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Ship> getAll(int pageNumber, int pageSize, String sort, Specification<Ship> spec) {
        Pageable pageableRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

        Page<Ship> ships = repository.findAll(spec, pageableRequest);

        return ships.getContent();
    }

    @Override
    public Optional<Ship> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Ship create(Ship ship) {
        return repository.save(ship);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Integer getCount() {
        return repository.findAll().size();
    }

//    @Override
//    public List<Ship> findByNameAndPlanet(String name, String planet) {
//        return repository.findByNameAndPlanet(name, planet);
//    }
}
