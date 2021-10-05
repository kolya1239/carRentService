package com.example.carrentservice.services;

import com.example.carrentservice.entities.Brand;
import com.example.carrentservice.entities.User;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;

import java.util.List;

public interface UserServiceInterface {
    public List<User> getUserList();
    public User findUserByPhone(String phone) throws NoSuchElementInDatabaseException;
    public void deleteUserByPhone(String phone) throws NoSuchElementInDatabaseException;
    public void addOrUpdateUser(User user);
}
