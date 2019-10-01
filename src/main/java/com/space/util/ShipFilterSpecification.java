package com.space.util;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;

public class ShipFilterSpecification {

    private ShipFilterSpecification() {
    }

    //метод для поиска по совпадениям в имени корабля и планете
    public static Specification<Ship> shipNameOrPlanetContainsIgnoreCase(String name) {
        return (root, query, cb) -> {
            String pattern = getContainsLikePattern(name);
            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("planet")), pattern)
            );
        };
    }

    private static String getContainsLikePattern(String name) {
        if (name == null || name.isEmpty()) {
            return "%";
        }
        else {
            return "%" + name.toLowerCase() + "%";
        }
    }
}
