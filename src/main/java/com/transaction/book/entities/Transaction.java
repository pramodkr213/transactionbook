package com.transaction.book.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double amount;
    private double balanceAmount;
    private String date;
    private String detail;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bill;

    private boolean deleteFlag;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    // In Transaction.java
@OneToMany(mappedBy = "transaction")
@JsonManagedReference
private List<HistryPayment> historyPayments;

// In HistryPayment.java
@ManyToOne(fetch = FetchType.LAZY)
@JsonBackReference
private Transaction transaction;

}
