package com.transaction.book.dto.responseDTO;



public class DaysAndInterestResponse {

	private long totalDays;
	private double interest;
	public long getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(long totalDays) {
		this.totalDays = totalDays;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	@Override
	public String toString() {
		return "DaysAndInterestResponse [totalDays=" + totalDays + ", interest=" + interest + "]";
	}	
}

