package com.transaction.book.entities;



import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tr_table")
public class TR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String trName;
    private String mediatorName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    // ✅ Customer reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    // ✅ Customer snapshot saved inside TR
    private String name;
    private String mobileNo;
    private double goldWgt;
    private double silverWgt;
    private String detail;
    private double goldTakenAmt;
    private double silverTakenAmt;
}

