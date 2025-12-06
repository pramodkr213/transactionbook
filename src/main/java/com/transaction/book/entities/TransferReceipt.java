package com.transaction.book.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transfer")
public class TransferReceipt
{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@OneToOne
		private Customer customer;
		
		private String TRName;
		
		private String MediatorName;
		
		private Date TRDate;
 
}