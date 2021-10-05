package com.example.carrentservice.services;

import com.example.carrentservice.entities.Car;
import com.example.carrentservice.entities.idClass.CarId;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;

import java.util.List;

public interface CarServiceInterface {
    public List<Car> getCarList();
    public List<Car> searchThroughAllCarsByAllFields(String searchText);
    public Car findCar(CarId plate);
    public Car findCarByMark(String mark);
    public void deleteCar(CarId plate);
    public void addOrUpdateCar(Car car);
}
