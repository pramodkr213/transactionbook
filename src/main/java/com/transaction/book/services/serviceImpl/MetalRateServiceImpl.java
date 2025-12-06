//package com.transaction.book.services.serviceImpl;
//
//
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.transaction.book.controller.RateController;
//import com.transaction.book.entities.Rate;
//import com.transaction.book.repository.RateRepository;
//import com.transaction.book.services.serviceInterface.MetalRateService;
//
//@Service
//public class MetalRateServiceImpl implements MetalRateService{
//
//	private static final Logger logger = LoggerFactory.getLogger(RateController.class);
//	
//	@Autowired
//	private RateRepository repo;
//	
//	
//	@Override
//	public Rate saveRateService(String metalName, double metalRate) {
//
//		Optional<Rate> existingRateOpt = repo.findByMetal(metalName);
//        logger.info("If Existing record is present: "+ existingRateOpt);
//        if (existingRateOpt.isPresent() || !existingRateOpt.isEmpty()) {	
//            Rate existingRate = existingRateOpt.get();
//            existingRate.setRate(metalRate);
//            existingRate.setCreatedAt(LocalDateTime.now());
//            logger.info("Updating Existing Record");
//            logger.info("Updated Metal Details Saving to DB : "+existingRate);
//            return repo.save(existingRate);
//        } else {
//            Rate newRate = new Rate();
//            newRate.setMetal(metalName);
//            newRate.setRate(metalRate);
//            newRate.setCreatedAt(LocalDateTime.now());
//            logger.info("Creating New Record");
//            logger.info("New Metal Details Saving to DB : "+newRate);
//            return repo.save(newRate);	
//	     }
//     }
//
//
//	@Override
//	public long totalDaysService(String startDate, String endDate) {
//		// TODO Auto-generated method stub
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		LocalDate start = LocalDate.parse(startDate,formatter);
//        LocalDate end = LocalDate.parse(endDate,formatter);
//
//         if (end.isBefore(start)) {
//            logger.info("Bad Request");;
//         }
//
//        long daysBetween = ChronoUnit.DAYS.between(start, end);
//
//		return daysBetween;
//	}	
//}
//


package com.transaction.book.services.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.book.entities.Rate;
import com.transaction.book.repository.RateRepository;
import com.transaction.book.services.serviceInterface.MetalRateService;

@Service
public class MetalRateServiceImpl implements MetalRateService {

    private static final Logger logger = LoggerFactory.getLogger(MetalRateServiceImpl.class);

    @Autowired
    private RateRepository repo;

    @Override
    public Rate saveRate(Double goldRate, Double silverRate) {
        Rate rate = new Rate();
        rate.setGoldLiveRate(goldRate);
        rate.setSilverLiveRate(silverRate);
        return repo.save(rate);
    }

    @Override
    public List<Rate> getLastThreeRates() {
        List<Rate> allRates = repo.findTop3ByOrderByCreatedAtDesc();
        return allRates;
    }
   

    @Override
    public long totalDaysService(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        if (end.isBefore(start)) {
            logger.info("Bad Request: End date before start date");
            return 0;
        }

        return ChronoUnit.DAYS.between(start, end);
    }
}
