package com.transaction.book.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "interest_record")
public class InterestRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    // metalType: "GOLD" or "SILVER"
    private String metalType;

    private BigDecimal originalAmount;      // goldTakenAmt or silverTakenAmt
    private BigDecimal cutAmount;           // 25% of originalAmount
    private BigDecimal internal75;          // 75% of cutAmount (initial)
    private BigDecimal remainingInternal;   // current remaining internal 75%
    private BigDecimal dailyInterest;       // computed daily interest (principal-based)

    private boolean finished = false;
    private LocalDate createdAt;
    private LocalDate lastProcessedDate;

    // getters / setters
    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getMetalType() { return metalType; }
    public void setMetalType(String metalType) { this.metalType = metalType; }

    public BigDecimal getOriginalAmount() { return originalAmount; }
    public void setOriginalAmount(BigDecimal originalAmount) { this.originalAmount = originalAmount; }

    public BigDecimal getCutAmount() { return cutAmount; }
    public void setCutAmount(BigDecimal cutAmount) { this.cutAmount = cutAmount; }

    public BigDecimal getInternal75() { return internal75; }
    public void setInternal75(BigDecimal internal75) { this.internal75 = internal75; }

    public BigDecimal getRemainingInternal() { return remainingInternal; }
    public void setRemainingInternal(BigDecimal remainingInternal) { this.remainingInternal = remainingInternal; }

    public BigDecimal getDailyInterest() { return dailyInterest; }
    public void setDailyInterest(BigDecimal dailyInterest) { this.dailyInterest = dailyInterest; }

    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDate getLastProcessedDate() { return lastProcessedDate; }
    public void setLastProcessedDate(LocalDate lastProcessedDate) { this.lastProcessedDate = lastProcessedDate; }
}