package com.example.carrentservice.entities;

import com.example.carrentservice.entities.idClass.CarId;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.enums.FuelType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cars")
@IdClass(CarId.class)
public class Car implements Serializable {
    @Id
    @Column(name = "license_plate")
    private String licencePlate;
    @Id
    @Column(name = "registration_code")
    private String registrationCode;
    @Column(name = "mark")
    private String mark;
    @Column(name = "description")
    private String description;
    @Column(name = "img", nullable = false, length = 200)
    private String img;
    @Column(name = "price")
    private float price;
    @Column(name = "car_class")
    @Enumerated(EnumType.STRING)
    private CarClass carClass;
    @Column(name = "car_power")
    private int carPower;
    @Column(name = "tank_capacity")
    private int tankCapacity;
    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;
    @OneToMany(mappedBy = "car")
    private List<Bill> bills;

    public Car() {
    }

    public Car(String licencePlate, String registrationCode, String mark, String description, String img, float price, CarClass carClass, int carPower, int tankCapacity, FuelType fuelType, Brand brand, List<Bill> bills) {
        this.licencePlate = licencePlate;
        this.registrationCode = registrationCode;
        this.mark = mark;
        this.description = description;
        this.img = img;
        this.price = price;
        this.carClass = carClass;
        this.carPower = carPower;
        this.tankCapacity = tankCapacity;
        this.fuelType = fuelType;
        this.brand = brand;
        this.bills = bills;
    }

    public CarId getId(){
        return new CarId(licencePlate, registrationCode);
    }

    public void setId(CarId carId){
        this.licencePlate = carId.getLicencePlate();
        this.registrationCode = carId.getRegistrationCode();
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public int getCarPower() {
        return carPower;
    }

    public void setCarPower(int carPower) {
        this.carPower = carPower;
    }

    public int getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(int tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Float.compare(car.price, price) == 0 && carPower == car.carPower && tankCapacity == car.tankCapacity && Objects.equals(licencePlate, car.licencePlate) && Objects.equals(registrationCode, car.registrationCode) && Objects.equals(mark, car.mark) && Objects.equals(description, car.description) && Objects.equals(img, car.img) && carClass == car.carClass && fuelType == car.fuelType && Objects.equals(brand, car.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licencePlate, registrationCode, mark, description, img, price, carClass, carPower, tankCapacity, fuelType, brand);
    }
}
