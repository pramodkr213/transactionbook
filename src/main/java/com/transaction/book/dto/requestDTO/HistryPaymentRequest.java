package com.transaction.book.dto.requestDTO;

import java.util.List;

import com.transaction.book.entities.HistryPayment;

import lombok.Data;

@Data
public class HistryPaymentRequest {
    
    private List<HistryPayment> historyPayments;

}
