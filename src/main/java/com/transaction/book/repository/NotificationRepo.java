package com.transaction.book.repository;

import com.transaction.book.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByCustomerId(Long customerId);
}