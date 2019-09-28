package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository repository;

    @Override
    public List<Ship> getAll() {
        return repository.findAll();
    }

    @Override
    public Ship getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public void save(Ship ship) {
        repository.save(ship);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
