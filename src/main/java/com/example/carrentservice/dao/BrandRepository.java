package com.example.carrentservice.dao;

import com.example.carrentservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Brand findBrandByName(String name);
}
