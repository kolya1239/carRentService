package com.example.carrentservice.services;

import com.example.carrentservice.entities.Bill;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;

import java.util.List;

public interface BillServiceInterface {
    public List<Bill> getBillList();
    public Bill findBill(int id) throws NoSuchElementInDatabaseException;
    public void deleteBill(int id) throws NoSuchElementInDatabaseException;
    public void addOrUpdateBill(Bill bill);
}
