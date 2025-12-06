package com.transaction.book.services.serviceImpl;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.book.dto.requestDTO.CurrentAndShopkeeperRateRequest;
import com.transaction.book.dto.responseDTO.CurrentAndShopkeeperRateResponse;
import com.transaction.book.dto.requestDTO.DateDifferenceRequest;
import com.transaction.book.dto.responseDTO.DateDifferenceResponse;
import com.transaction.book.dto.requestDTO.DaysAndInterestRequest;
import com.transaction.book.dto.responseDTO.DaysAndInterestResponse;
import com.transaction.book.entities.Rate;
import com.transaction.book.repository.RateRepository;
import com.transaction.book.services.serviceInterface.CalculatorService;

@Service
public class CalculatorServiceImpl implements CalculatorService{

private static final Logger logger = LogManager.getLogger(CalculatorServiceImpl.class);
	
    @Autowired
    private RateRepository rateRepository;
    
	@SuppressWarnings("null")
	@Override
	public DaysAndInterestResponse calculateDaysAndInterest(DaysAndInterestRequest request) {

	    DaysAndInterestResponse response = new DaysAndInterestResponse();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try {
	        LocalDate start = LocalDate.parse(request.getStartDate(), formatter);
	        LocalDate end = LocalDate.parse(request.getEndDate(), formatter);

	        logger.info("Parsed Start: " + start + " End: " + end);

	        if (end.isBefore(start)) {
	            throw new IllegalArgumentException("End date should not be before Start date.");
	        }

	        // --- Apply 30/360 Rule ---
	        int d1 = Math.min(start.getDayOfMonth(), 30);
	        int d2 = Math.min(end.getDayOfMonth(), 30);

	        int m1 = start.getMonthValue();
	        int m2 = end.getMonthValue();

	        int y1 = start.getYear();
	        int y2 = end.getYear();

	        long totalDays = (y2 - y1) * 360L + (m2 - m1) * 30L + (d2 - d1);

	        logger.info("Calculated Total Days (360-day year): " + totalDays);

	        // ---- Interest Calculation ----
	        double customerAmount = request.getCustomerAmount();
	        double interest = request.getInterest();

	        double monthlyInterest = customerAmount * (interest / 100.0);
	        double perDayInterest = monthlyInterest / 30;

	        double totalInterest;
	        if (totalDays <= 30) {
	            totalInterest = monthlyInterest;
	        } else {
	            int extraDays = (int) (totalDays - 30);
	            totalInterest = monthlyInterest + (perDayInterest * extraDays);
	        }

	        response.setTotalDays(totalDays);
	        response.setInterest(totalInterest);

	        return response;

	    } catch (Exception e) {
	        logger.error("Error while calculating days/interest: " + e.getMessage(), e);
	        throw new RuntimeException("Failed to calculate days and interest", e);
	    }
	    
//	@Override
//	public DaysAndInterestResponse calculateDaysAndInterest(DaysAndInterestRequest request) {
//		    
//		    DaysAndInterestResponse daysAndInterestResponse = new DaysAndInterestResponse();
//		    //DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");
//		    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		    		                                          // .withResolverStyle(ResolverStyle.STRICT);
//		    LocalDate start;
//		    LocalDate end;
//			LocalDate today = LocalDate.now();
//		    long totalDays;
//		    
//		    try {
//		        // Validate and parse the dates
//		        start = LocalDate.parse(request.getStartDate(), outputFormatter);
//		        end = LocalDate.parse(request.getEndDate(), outputFormatter);
//		        logger.info("Date after parsing :Start Date"+start+"End Date:"+end);
//		    } catch (DateTimeParseException e) {
//		    	logger.error("Invalid Date Format");
//		        throw new IllegalArgumentException("Invalid date format! Please use dd-MM-yyyy.", e);
//		    }
//
//	
//
//	if (end.isBefore(start)) {
//    logger.error("End date should not be before Start Date");
//    throw new IllegalArgumentException("End date should not be before Start Date.");
//}
//else {
//    totalDays = ChronoUnit.DAYS.between(start, end);
//    logger.info("Total Date Difference in Real Calendar is:" + totalDays);
//    logger.info("Adjusting the days according 360 days Year.");
//
//    // Adjust for December
//    LocalDate date = start.withDayOfMonth(1);
//    while (!date.isAfter(end)) {
//        if (date.getMonthValue() == 12) {
//            if (date.isLeapYear()) {
//                totalDays -= 6; // Leap year December
//                logger.info("Inside Leap year condition");
//            } else {
//                totalDays -= 5; // Normal year December
//            }
//        }
//        date = date.plusMonths(1);
//    }
//
//    double customerAmount = request.getCustomerAmount();
//    double interest = request.getInterest();
//
//    double monthlyInterest = customerAmount * (interest / 100.0);
//    double perDayInterest = monthlyInterest / 30;
//
//    if (totalDays <= 30) {
//        daysAndInterestResponse.setTotalDays(totalDays);
//        daysAndInterestResponse.setInterest(monthlyInterest);
//        return daysAndInterestResponse;
//    } else {
//        int extraDays = (int) (totalDays - 30);
//        double totalInterest = monthlyInterest + (perDayInterest * extraDays);
//
//        daysAndInterestResponse.setTotalDays(totalDays);
//        daysAndInterestResponse.setInterest(totalInterest);
//        return daysAndInterestResponse;
//    
//}
//}
	
}
}