package com.transaction.book.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class CustomerNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String customerName;
    private String mobileNo;
    private LocalDate notifiedDate;
    private String message;

    private boolean sent = false; // true once notification sent
}