package com.transaction.book.services.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.transaction.book.dto.responseDTO.CusotomerFullResponse;
import com.transaction.book.dto.responseDTO.CustomerResponse;
import com.transaction.book.dto.responseDTO.DueDate;
import com.transaction.book.dto.responseDTO.OldCustomerResponse;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.Transaction;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.TransactionRepo;
import com.transaction.book.services.serviceInterface.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;
    
   
    public List<OldCustomerResponse> getOldCustomers() {
        List<Object[]> result = customerRepo.findOldCustomerNamesAndMobiles();
        return result.stream()
                .map(row -> new OldCustomerResponse(
                        (String) row[0], // name
                        (String) row[1]  // mobile_no
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerByMobileNo(String mobileNo) {
        return this.customerRepo.findByMobileNo(mobileNo);
    }

    @Override
    public Customer getCustomerById(long id) {
        return this.customerRepo.findById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepo.findAllCustomers();
    }

    @Override
    public void deleteCusotmer(long id) {
        Customer customer = this.customerRepo.findById(id);
        if (customer.getTransactions() != null) {
            for (Transaction transaction : customer.getTransactions()) {
                transaction.setDeleteFlag(true);
                this.transactionRepo.save(transaction);
            }
        }
        customer.setDeleteFlag(true);
        this.customerRepo.save(customer);
        return;
    }

    @Override
    public double getTotalGetAmount() {
        return this.customerRepo.getTotalGetAmount();
    }

    @Override
    public double getToalGaveAmount() {
        return this.customerRepo.getTotalGaveAmount();
    }

    @Override
    public CusotomerFullResponse getCustomerResponseById(long id) {
        return this.customerRepo.findCustomerResponseById(id);
    }

    @Override
    public List<CustomerResponse> findAllCustomerResponse(String query, boolean gave, boolean get, boolean settel) {
        return this.customerRepo.findAllCustomerResponse(query, gave, get, settel);
    }

    @Override
    public DueDate getDueDateCustomer() {
        DueDate dueDate = new DueDate();
        dueDate.setTodaysDueDate(this.customerRepo.findTodaysDueDateCusotmers(String.valueOf(LocalDate.now())));
        dueDate.setTomorrowDueDate(
                this.customerRepo.findTodaysDueDateCusotmers(String.valueOf(LocalDate.now().plusDays(1))));
        dueDate.setNotPaymentYet(this.customerRepo.findDueDateCusotmersNotPaymentYet(String.valueOf(LocalDate.now())));
        dueDate.setAllDueConstomers(this.customerRepo.findDueDateCusotmers());
        dueDate.setReminderOnCustomers(this.customerRepo.findSetReminderOnCustomers());
        dueDate.setTagadaCustomers(this.customerRepo.findTagadaCustomers());
        return dueDate;
    }

    @Override
    public CusotomerFullResponse getCustomerResponseByName(String name) {
        return this.customerRepo.findCustomerResponseByName(name);
    }

    @Override
    public void setTagada(long customerId, String date) throws Exception {
        Customer customer = this.getCustomerById(customerId);

        if (customer == null) {
            throw new Exception("Customer Not Found!!");
        }

        customer.setTagada(true);
        customer.setTagadaDate(date);

        this.customerRepo.save(customer);
    }
    
    @Override
    public void deleteTagada(long customerId) throws Exception {
        Customer customer = this.getCustomerById(customerId);
        if (customer == null) {
            throw new Exception("Customer Not Found!!");
        }
        customer.setTagada(true);
        this.customerRepo.save(customer);
        // TODO Auto-generated method stub
        
    }
}
