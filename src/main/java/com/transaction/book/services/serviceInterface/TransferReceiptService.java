package com.transaction.book.services.serviceInterface;

import com.transaction.book.dto.requestDTO.TransferReceiptRequest;
import com.transaction.book.entities.TransferReceipt;

public interface TransferReceiptService 
{
	 TransferReceipt addTransferReceipt(TransferReceiptRequest request);
	    TransferReceipt findByCustomerId(Long customerId);
}
