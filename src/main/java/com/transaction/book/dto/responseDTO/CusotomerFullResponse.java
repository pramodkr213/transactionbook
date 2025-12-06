package com.transaction.book.dto.responseDTO;

import org.springframework.data.repository.query.Param;

import com.transaction.book.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CusotomerFullResponse {
    private long id;
    private String name;
    private String mobileNo;
    private String gstinNo;
    private double amount;
    private String dueDate;
    private String updateDate;
    private Address address;
    private String reference;
    private double interest;
    private String detail;
    private double goldSellingAmt;
    private double goldTakenAmt;
    private double goldWgt;
    private double silverSellingAmt;
    private double silverTakenAmt;
    private double silverWgt;
    
 
}
