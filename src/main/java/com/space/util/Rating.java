package com.space.util;

import java.sql.Date;

public class Rating {

    public static double ratingCalc(Boolean isUsed, Double speed, Date prodDate) {
        double k;
        if (isUsed){
            k = 0.5;
        } else k = 1.0;
        return (double)Math.round(100 * (80 * speed * k) / (3019 - prodDate.toLocalDate().getYear() + 1)) / 100;
    }
}
