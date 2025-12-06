package com.transaction.book.repository;

import com.transaction.book.entities.Kato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KatoRepository extends JpaRepository<Kato, Integer> {
}