package com.transaction.book.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.book.dto.requestDTO.DaysAndInterestRequest;
import com.transaction.book.dto.responseDTO.DaysAndInterestResponse;
import com.transaction.book.services.serviceInterface.CalculatorService;

@RestController
public class CalculatorController {

	private static final Logger logger = LogManager.getLogger(CalculatorController.class);
	
	@Autowired
	private  CalculatorService calculatorService;
	
	
	// call:--  http://localhost:8080/difference
	@PostMapping("/totaldaysAndInterest")
	public ResponseEntity<DaysAndInterestResponse> getDaysAndInterest
	                               (@RequestBody DaysAndInterestRequest daysAndInterestRequest) {
		
		if ((daysAndInterestRequest == null || daysAndInterestRequest.getStartDate() == null || 
			daysAndInterestRequest.getStartDate().trim().isEmpty() || daysAndInterestRequest.getEndDate() == null 
		    || daysAndInterestRequest.getEndDate().trim().isEmpty()) || daysAndInterestRequest.getCustomerAmount()<=0
		    || daysAndInterestRequest.getInterest()<=0) {
	        logger.error("Request Details not found : All parameters are required");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(null);
	    }
		
		logger.info("Input Parameters are :" + daysAndInterestRequest);

		DaysAndInterestResponse response = null;
	    
	    try {
	       response = calculatorService.calculateDaysAndInterest(daysAndInterestRequest);
	    } catch(Exception e)
	    {
	    	logger.error("Error occurred while processing request: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null);
	    }
	    
	    logger.info("End of Date Difference Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
/*	
	@PostMapping("/calculate/currentAndShopkeeperRate")
	public ResponseEntity<CurrentAndShopkeeperRateResponse> calculateCurrentAndShopkeeperRate
    (@RequestBody CurrentAndShopkeeperRateRequest currentAndShopkeeperRateRequest) {
		
		logger.info("Input Parameters are :" + currentAndShopkeeperRateRequest);
		
		if(currentAndShopkeeperRateRequest == null || currentAndShopkeeperRateRequest.getMetalName() == null
		    || currentAndShopkeeperRateRequest.getMetalPrice()<=0 || currentAndShopkeeperRateRequest.getWeight()<= 0
		    || currentAndShopkeeperRateRequest.getMetalName().trim() == null
		    || currentAndShopkeeperRateRequest.getMetalName().trim().isEmpty()
			) 
		{
			logger.error("Request Details not found : All Parameter required");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(null);	
	     }
		
		
		CurrentAndShopkeeperRateResponse response = null;
		
		try {
			response = calculatorService.calculateShopkeeperAndCurrentRate(currentAndShopkeeperRateRequest);
		}
		catch(Exception e)
		{
			logger.error("Error occurred while processing request: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null);
		}
      
		return new ResponseEntity<>(response, HttpStatus.OK);
   }
   */
}

