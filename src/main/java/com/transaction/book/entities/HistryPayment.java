package com.transaction.book.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hst_pymt")
public class HistryPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String date;
    private Double amount;
    private String mode;
    private String note;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id") // ensure this matches your DB schema
    private Transaction transaction;

    // ✅ Add Getters and Setters — needed for serialization and deserialization
    // Optionally add equals, hashCode, toString (but be careful with LAZY relationships)

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }
}
