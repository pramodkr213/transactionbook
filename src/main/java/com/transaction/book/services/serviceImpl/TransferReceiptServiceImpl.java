package com.transaction.book.services.serviceImpl;


import org.springframework.stereotype.Service;

import com.transaction.book.dto.requestDTO.TransferReceiptRequest;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.TransferReceipt;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.TransferReceiptRespo;
import com.transaction.book.services.serviceInterface.TransferReceiptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferReceiptServiceImpl implements TransferReceiptService
{
	
	private final TransferReceiptRespo transferReceiptRespo;
    private final CustomerRepo customerRepo;

    @Override
    public TransferReceipt addTransferReceipt(TransferReceiptRequest request) {
        log.info("Adding Transfer Receipt for customerId: {}", request.getCustomerId());
        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        TransferReceipt receipt = new TransferReceipt();
        receipt.setCustomer(customer);
        receipt.setTRName(request.getTRName());
        receipt.setMediatorName(request.getMediatorName());
        receipt.setTRDate(request.getTRDate());

        TransferReceipt saved = transferReceiptRespo.save(receipt);
        log.info("Transfer Receipt saved successfully with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public TransferReceipt findByCustomerId(Long customerId) {
        log.info("Fetching Transfer Receipt for customerId: {}", customerId);
        return transferReceiptRespo.findByCustomerId(customerId);
    }
}
