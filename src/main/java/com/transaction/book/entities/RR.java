package com.transaction.book.entities;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "rr_table")
public class RR {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;


private String mediatorName;


@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate date;


// From TR snapshot
private String trName;


// Customer snapshot
private String name;
private String mobileNo;
private double goldWgt;
private double silverWgt;
private String detail;
private double goldTakenAmt;
private double silverTakenAmt;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id")
@JsonIgnore
private Customer customer;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "tr_id")
@JsonIgnore
private TR tr;
}


