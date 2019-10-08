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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Basic
    @Column(name = "planet")
    @NotBlank
    @Size(max = 50)
    private String planet;

    @Basic
    @Column(name = "shipType")
    private String shipType;

    @Basic
    @Column(name = "prodDate")
    @NotNull
    private Date prodDate;

    @Basic
    @Column(name = "isUsed")
    private Boolean isUsed = false;

    @Basic
    @Column(name = "speed")
    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "0.99")
    private Double speed;

    @Basic
    @Column(name = "crewSize")
    @NotNull
    @Range(min = 1, max = 9999)
    private Integer crewSize;

    @Basic
    @Column(name = "rating")
    private Double rating;

    public Ship() {
    }

    public Ship(String name, String planet, String shipType, Date prodDate, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.speed = speed;
        this.crewSize = crewSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        prodDate.setMonth(Calendar.JANUARY);
        prodDate.setDate(1);
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

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

        Ship that = (Ship) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(planet, that.planet)) return false;
        if (!Objects.equals(shipType, that.shipType)) return false;
        if (!Objects.equals(prodDate, that.prodDate)) return false;
        if (!Objects.equals(isUsed, that.isUsed)) return false;
        if (!Objects.equals(speed, that.speed)) return false;
        if (!Objects.equals(crewSize, that.crewSize)) return false;
        return Objects.equals(rating, that.rating);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (planet != null ? planet.hashCode() : 0);
        result = 31 * result + (shipType != null ? shipType.hashCode() : 0);
        result = 31 * result + (prodDate != null ? prodDate.hashCode() : 0);
        result = 31 * result + (isUsed != null ? isUsed.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (crewSize != null ? crewSize.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
