package com.space.util;

import com.space.model.Ship;

public class ShipValidator {

    public static boolean validShipFields(Ship ship) {
        return (ship.getName() == null && ship.getPlanet() == null && ship.getSpeed() == null &&
                ship.getShipType() == null) || ship.getName().equals("") || ship.getPlanet().equals("") ||
                ship.getShipType() == null || ship.getSpeed() == null || ship.getCrewSize() == null ||
                ship.getProdDate().toLocalDate() == null || ship.getProdDate().toLocalDate().getYear() < 2800 ||
                ship.getProdDate().toLocalDate().getYear() > 3019 ||
                ship.getCrewSize() < 1 || ship.getCrewSize() > 9999 || ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99 ||
                ship.getName().length() > 50 || ship.getPlanet().length() > 50;
    }

    public static boolean validShipNeedFields(Ship ship) {
        return ship.getName().equals("") && ship.getPlanet().equals("") && ship.getSpeed() == null
                && ship.getProdDate() == null && ship.getCrewSize() == null && ship.getShipType() == null
                && ship.getUsed() == null;
    }

}
