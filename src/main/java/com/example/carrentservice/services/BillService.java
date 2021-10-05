package com.example.carrentservice.services;

import com.example.carrentservice.dao.BillRepository;
import com.example.carrentservice.entities.Bill;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService implements BillServiceInterface {

    private BillRepository billRepository;

    public BillService() {
    }

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public List<Bill> getBillList() {
        return billRepository.findAll();
    }

    @Override
    public Bill findBill(int id) throws NoSuchElementInDatabaseException {
        Bill Bill = billRepository.getById(id);
        return ifBillFound(Bill);
    }

    @Override
    public void deleteBill(int id) throws NoSuchElementInDatabaseException {
        Bill Bill = findBill(id);
        billRepository.delete(Bill);
    }

    @Override
    public void addOrUpdateBill(Bill bill) {
        billRepository.save(bill);
    }

    private Bill ifBillFound(Bill bill) throws NoSuchElementInDatabaseException {
        if (bill == null) {
            throw new NoSuchElementInDatabaseException("You tried to find a bill, but there is no requested bill in database.\n " +
                    "Please again try later.");
        }
        return bill;
    }
}
