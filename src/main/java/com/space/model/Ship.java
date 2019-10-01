package com.space.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship {

    private Long id;
    private String name;
    private String planet;
    private String shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship() {
    }

    public Ship(String name, String planet, String shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    @Size(min = 1, max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "planet")
    @Size(min = 1, max = 50)
    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    @Basic
    @Column(name = "shipType")
    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    @Basic
    @Column(name = "prodDate")
//    @Pattern(regexp = "(2[89]\\d\\d|30[01]\\d)")
    public Date getProdDate() {
        prodDate.setMonth(Calendar.JANUARY);
        prodDate.setDate(1);
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Basic
    @Column(name = "isUsed")
    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    @Basic
    @Column(name = "speed")
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "0.99")
    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "crewSize")
    @Range(min = 1, max = 9999)
    @NotNull
    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    @Basic
    @Column(name = "rating")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Objects.equals(id, ship.id) &&
                Objects.equals(name, ship.name) &&
                Objects.equals(planet, ship.planet) &&
                shipType == ship.shipType &&
                Objects.equals(prodDate, ship.prodDate) &&
                Objects.equals(isUsed, ship.isUsed) &&
                Objects.equals(speed, ship.speed) &&
                Objects.equals(crewSize, ship.crewSize) &&
                Objects.equals(rating, ship.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }
}
