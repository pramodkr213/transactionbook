package com.transaction.book.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transaction.book.entities.TR;

public interface TRRepository extends JpaRepository<TR, Integer> {
    List<TR> findByCustomerId(Long customerId);
}


