package com.example.carrentservice.exceptions;

public class NoSuchElementInDatabaseException extends Exception{
    public NoSuchElementInDatabaseException(String message) {
        super(message);
    }
}
