package com.transaction.book.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.transaction.book.entities.Rate;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {

    // ✅ Get last 3 rates sorted by createdAt (latest first)
    @Query("SELECT r FROM Rate r ORDER BY r.createdAt DESC")
    List<Rate> findTop3ByOrderByCreatedAtDesc();
}
