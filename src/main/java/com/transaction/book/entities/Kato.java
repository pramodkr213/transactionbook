package com.transaction.book.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "kato_table")
public class Kato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String receiverName;

    // Snapshot from customer
    private String name;
    private String mobileNo;
    private double goldWgt;
    private double silverWgt;
    private String detail;
    private double goldTakenAmt;
    private double silverTakenAmt;
    private String updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}