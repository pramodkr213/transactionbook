package com.transaction.book.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerResponse {
    private long id;
    private String name;
    private String mobileNo;
    private double amount;
    private String dueDate;
    private String updateDate;
    private boolean isTagada;
    private String tagadaDate;

    private double goldSellingAmt;
    private double goldTakenAmt;
    private double goldWgt;
    private double silverSellingAmt;
    private double silverTakenAmt;
    private double silverWgt;
    private double interest;
    private String detail;

    // ✅ Constructor for the 8-field query

	public CustomerResponse(long id, String name, String mobileNo, double amount, String dueDate, String updateDate,
			boolean isTagada, String tagadaDate, double goldSellingAmt, double goldTakenAmt, double goldWgt,
			double silverSellingAmt, double silverTakenAmt, double silverWgt, double interest, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNo = mobileNo;
		this.amount = amount;
		this.dueDate = dueDate;
		this.updateDate = updateDate;
		this.isTagada = isTagada;
		this.tagadaDate = tagadaDate;
		this.goldSellingAmt = goldSellingAmt;
		this.goldTakenAmt = goldTakenAmt;
		this.goldWgt = goldWgt;
		this.silverSellingAmt = silverSellingAmt;
		this.silverTakenAmt = silverTakenAmt;
		this.silverWgt = silverWgt;
		this.interest = interest;
		this.detail = detail;
	}
       
}