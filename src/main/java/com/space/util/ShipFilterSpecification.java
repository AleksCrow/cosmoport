package com.space.util;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.Calendar;

public class ShipFilterSpecification {

    private ShipFilterSpecification() {
    }

    //метод для поиска по совпадениям в имени корабля и планете
    public static Specification<Ship> shipNameContainsIgnoreCase(String string) {
        return (root, query, cb) -> {
            String pattern = getContainsLikePattern(string);
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<Ship> shipPlanetContainsIgnoreCase(String string) {
        return (root, query, cb) -> {
            String pattern = getContainsLikePattern(string);
            return cb.like(cb.lower(root.get("planet")), pattern);
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

    public static Specification<Ship> shipTypeSpecification(ShipType shipType) {
        return (root, query, cb) -> {
            if (shipType == ShipType.MERCHANT || shipType == ShipType.MILITARY || shipType == ShipType.TRANSPORT) {
                return cb.equal(root.get("shipType"), shipType.toString());
            }
            return null;
        };
    }

    public static Specification<Ship> SpeedAndRatingRange(Double min, Double max, String filter) {
        return (root, query, cb) -> cb.between(root.get(filter), min, max);
    }

    public static Specification<Ship> crewSizeRange(Integer min, Integer max, String filter) {
        return (root, query, cb) -> cb.between(root.get(filter), min, max);
    }

    public static Specification<Ship> prodDateRange(Long after, Long before){
        return (root, query, cb) -> {

            Date prodAfter = new Date(after);
            prodAfter.setDate(1);
            prodAfter.setMonth(Calendar.JANUARY);
            Date prodBefore = new Date(before);
            prodBefore.setDate(31);
            prodBefore.setMonth(Calendar.DECEMBER);

            if (after < 26192302747154l || after > 33134715547154l) {
                prodAfter = new Date(26192302747154l);
            }
            if (before > 33134715547154l || before < 26192302747154l) {
                prodBefore = new Date(33134715547154l);
            }

            return cb.between(root.get("prodDate"), prodAfter, prodBefore);
        };
    }

    public static Specification<Ship> isUsed(Boolean isUsed) {
        if (isUsed == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("isUsed"), isUsed);
    }

}
