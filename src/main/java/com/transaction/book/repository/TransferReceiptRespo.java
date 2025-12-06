package com.transaction.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transaction.book.entities.TransferReceipt;

public interface TransferReceiptRespo extends JpaRepository<TransferReceipt, Long>
{
	
	@Query("SELECT t FROM TransferReceipt t WHERE t.customer.id = :customerId")
    TransferReceipt findByCustomerId(@Param("customerId") Long customerId);
}
