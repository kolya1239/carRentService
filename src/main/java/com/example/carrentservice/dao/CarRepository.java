package com.example.carrentservice.dao;

import com.example.carrentservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    public Car findCarByMark(String mark);
    @Query("select distinct car from Car car where lower(car.registrationCode) like %:searchText% or lower(car.licencePlate) like %:searchText% or lower(car.mark) like %:searchText% or lower(car.description) like %:searchText% or lower(car.carClass) like %:searchText% or lower(car.fuelType) like %:searchText%")
    public List<Car> findDistinctByAllStringColumns(String searchText);
    @Query(value = "SELECT * FROM cars WHERE price LIKE %?1% OR car_power LIKE %?1% OR tank_capacity LIKE %?1%", nativeQuery = true)
    public List<Car> findDistinctByAllNumericColumns(String searchNumber);
    public Car findCarByLicencePlate(String licencePlate);
    public Car findCarByLicencePlateAndRegistrationCode(String licencePlate, String code);
}
