package com.transaction.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transaction.book.entities.HistryPayment;
@Repository
public interface HistryPaymentRepository extends JpaRepository<HistryPayment, Long> {
    
    
    @Query("SELECT hp FROM HistryPayment hp WHERE hp.transaction.customer.id = :customerId")
    List<HistryPayment> findByCustomerId(@Param("customerId") Long customerId);
}
