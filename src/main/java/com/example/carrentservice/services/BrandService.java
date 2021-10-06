package com.example.carrentservice.services;

import com.example.carrentservice.dao.BrandRepository;
import com.example.carrentservice.entities.Brand;
import com.example.carrentservice.entities.Car;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements BrandServiceInterface {

    private BrandRepository brandRepository;

    public BrandService() {
    }

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<String> getBrandNameList() {
        List<Brand> brandList = brandRepository.findAll();
        return brandList.stream().map(Brand::getName).collect(Collectors.toList());
    }

    @Override
    public List<Brand> getBrandList() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findBrand(int id) throws NoSuchElementInDatabaseException {
        Brand brand = brandRepository.getById(id);
        return ifBrandFound(brand);
    }

    @Override
    public Brand findBrandByName(String name) throws NoSuchElementInDatabaseException {
        Brand brand = brandRepository.findBrandByName(name);
        return ifBrandFound(brand);
    }

    @Override
    public void deleteBrand(int id) throws NoSuchElementInDatabaseException {
        Brand brand = findBrand(id);
        brandRepository.delete(brand);
    }

    @Override
    public void addOrUpdateBrand(Brand brand) {
        brandRepository.saveAndFlush(brand);
    }

    private Brand ifBrandFound(Brand brand) throws NoSuchElementInDatabaseException {
        if (brand == null) {
            throw new NoSuchElementInDatabaseException("You tried to find a brand, but there is no requested brand in database.\n " +
                    "Please again try later.");
        }
        return brand;
    }

    public List<CarClass> getExistingCarClassList(Brand brand){
        List<CarClass> carClassList = new ArrayList<CarClass>();
        List<Car> carList = brand.getCars();
        for (Car car : carList) {
            CarClass carClass = car.getCarClass();
            if(!carClassList.contains(carClass)){
                carClassList.add(carClass);
            }
        }
        return carClassList;
    }

    public List<Double> getAveragePriceList() {
        List<Double> averagePriceList = new ArrayList<Double>();
        for (Brand brand : getBrandList()) {
            List<Car> carList = brand.getCars();
            if (carList.size() > 0) {
                averagePriceList.add((double) ((carList.stream().mapToDouble(Car::getPrice).sum() / carList.size())));
            } else {
                averagePriceList.add(0.0);
            }
        }
        return averagePriceList;
    }
}
