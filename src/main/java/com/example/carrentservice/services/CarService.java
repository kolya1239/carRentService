package com.example.carrentservice.services;

import com.example.carrentservice.dao.CarRepository;
import com.example.carrentservice.entities.Car;
import com.example.carrentservice.entities.idClass.CarId;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarService implements CarServiceInterface {

    private CarRepository carRepository;

    public CarService() {
    }

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCarList() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> searchThroughAllCarsByAllFields(String searchText) {
        List<Car> carListStringFieldsSearch = carRepository.findDistinctByAllStringColumns(searchText);
        if(tryParseStringToInt(searchText) == null) {
            return carListStringFieldsSearch;
        } else {
            List<Car> carListIntFieldsSearch = carRepository.findDistinctByAllNumericColumns(searchText);
            Set<Car> finalCarSet = Stream.concat(carListStringFieldsSearch.stream(), carListIntFieldsSearch.stream()).collect(Collectors.toSet());
            return new ArrayList<>(finalCarSet);
        }
    }

    @Override
    public Car findCar(CarId id){
        Car car = carRepository.findCarByLicencePlateAndRegistrationCode(id.getLicencePlate(), id.getRegistrationCode());
        return car;
    }

    @Override
    public Car findCarByMark(String mark){
        Car car = carRepository.findCarByMark(mark);
        return car;
    }

    @Override
    public void deleteCar(CarId plate){
        Car car = findCar(plate);
        carRepository.delete(car);
    }

    @Override
    public void addOrUpdateCar(Car car) {
        carRepository.save(car);
    }

    public List<String> getCarLicencePlateList(){
        return getCarList().stream().map(Car::getId).map(CarId::getLicencePlate).collect(Collectors.toList());
    }

    public List<String> getCarRegistrationCodeList(){
        return getCarList().stream().map(Car::getId).map(CarId::getRegistrationCode).collect(Collectors.toList());
    }

    private Car ifCarFound(Car car) throws NoSuchElementInDatabaseException {
        if (car == null) {
            throw new NoSuchElementInDatabaseException("You tried to find a car, but there is no requested car in database.\n " +
                    "Please try again later.");
        }
        return car;
    }

    public static Integer tryParseStringToInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
