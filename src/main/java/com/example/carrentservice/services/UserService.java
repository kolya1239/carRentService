package com.example.carrentservice.services;

import com.example.carrentservice.dao.UserRepository;
import com.example.carrentservice.entities.Brand;
import com.example.carrentservice.entities.User;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface{

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByPhone(String phone){
        return userRepository.findUserByPhone(phone);
    }

    @Override
    public void deleteUserByPhone(String phone) {
        User user = findUserByPhone(phone);
        userRepository.delete(user);
    }

    @Override
    public void addOrUpdateUser(User user) {
        userRepository.save(user);
    }

    private User ifUserFound(User user) throws NoSuchElementInDatabaseException {
        if (user == null) {
            throw new NoSuchElementInDatabaseException("You tried to find a user, but there is no requested user in database.\n " +
                    "Please again try later.");
        }
        return user;
    }
}
