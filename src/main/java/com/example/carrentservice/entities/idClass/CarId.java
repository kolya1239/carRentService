package com.example.carrentservice.entities.idClass;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CarId implements Serializable {
    private String licencePlate;
    private String registrationCode;

    public CarId() {
    }

    public CarId(String licencePlate, String registrationCode) {
        this.licencePlate = licencePlate;
        this.registrationCode = registrationCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarId carId = (CarId) o;
        return Objects.equals(licencePlate, carId.licencePlate) && Objects.equals(registrationCode, carId.registrationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licencePlate, registrationCode);
    }

    @Override
    public String toString() {
        return "CarId{" +
                "licencePlate='" + licencePlate + '\'' +
                ", registrationCode='" + registrationCode + '\'' +
                '}';
    }
}
