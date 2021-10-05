package com.example.carrentservice.services;

import com.example.carrentservice.entities.Brand;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;

import java.util.List;

public interface BrandServiceInterface {
    public List<Brand> getBrandList();
    public List<String> getBrandNameList();
    public List<CarClass> getExistingCarClassList(Brand brand);
    public Brand findBrand(int id) throws NoSuchElementInDatabaseException;
    public Brand findBrandByName(String name) throws NoSuchElementInDatabaseException;
    public void deleteBrand(int id) throws NoSuchElementInDatabaseException;
    public void addOrUpdateBrand(Brand brand);
}
