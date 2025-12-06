package com.transaction.book.dto.requestDTO;


public class DaysAndInterestRequest {

	private double customerAmount;
	private double interest;
	private String startDate;
	private String endDate;
	public double getCustomerAmount() {
		return customerAmount;
	}
	public void setCustomerAmount(double customerAmount) {
		this.customerAmount = customerAmount;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "DaysAndInterestRequest [customerAmount=" + customerAmount + ", interest=" + interest + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
}

