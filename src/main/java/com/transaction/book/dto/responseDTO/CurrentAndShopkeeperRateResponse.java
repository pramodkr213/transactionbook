package com.transaction.book.dto.responseDTO;



public class CurrentAndShopkeeperRateResponse {
	
	private double currentRate;
	
	private double shopkeeperRate;

	public double getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(double currentRate) {
		this.currentRate = currentRate;
	}

	public double getShopkeeperRate() {
		return shopkeeperRate;
	}

	public void setShopkeeperRate(double shopkeeperRate) {
		this.shopkeeperRate = shopkeeperRate;
	}
}

