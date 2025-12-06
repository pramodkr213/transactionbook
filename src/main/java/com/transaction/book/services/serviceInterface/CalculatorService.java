package com.transaction.book.services.serviceInterface;



import com.transaction.book.dto.requestDTO.CurrentAndShopkeeperRateRequest;
import com.transaction.book.dto.responseDTO.CurrentAndShopkeeperRateResponse;
import com.transaction.book.dto.requestDTO.DaysAndInterestRequest;
import com.transaction.book.dto.responseDTO.DaysAndInterestResponse;

public interface CalculatorService {
	
   DaysAndInterestResponse calculateDaysAndInterest(DaysAndInterestRequest request);
	
 //  CurrentAndShopkeeperRateResponse calculateShopkeeperAndCurrentRate(CurrentAndShopkeeperRateRequest request);

}

