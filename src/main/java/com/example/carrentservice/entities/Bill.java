package com.example.carrentservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "rent_start_date")
    private Date rentStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rent_end_date")
    private Date rentEndDate;
    @Column(name = "total_price")
    private float totalPrice;
    @ManyToOne
    @JoinColumn(name = "user_phone")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "car_license_plate"),
            @JoinColumn(name = "car_registration_code")
    })
    @JsonIgnore
    private Car car;

    public Bill() {
    }

    public Bill(int id, Date rentStartDate, Date rentEndDate, float totalPrice, User user, Car car) {
        this.id = id;
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(Date rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public Date getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(Date rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
