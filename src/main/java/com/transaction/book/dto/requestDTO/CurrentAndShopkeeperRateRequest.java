package com.transaction.book.dto.requestDTO;



public class CurrentAndShopkeeperRateRequest {

	private String metalName;
	
    private double metalPrice;
	
	private double weight;	
	
	public String getMetalName() {
		return metalName;
	}

	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}

	public double getMetalPrice() {
		return metalPrice;
	}

	public void setMetalPrice(double metalPrice) {
		this.metalPrice = metalPrice;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "CurrentAndShopkeeperRateRequest [metalName=" + metalName + ", metalPrice=" + metalPrice + ", weight="
				+ weight + "]";
	}	
}

