package com.transaction.book.repository;

import com.transaction.book.entities.InterestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterestRecordRepo extends JpaRepository<InterestRecord, Long> {
    List<InterestRecord> findByFinishedFalse();
    InterestRecord findTopByCustomerIdAndMetalTypeOrderByCreatedAtDesc(Long customerId, String metalType);
}