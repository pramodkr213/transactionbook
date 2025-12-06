//package com.transaction.book.services.serviceInterface;
//
//
//
//import java.time.LocalDate;
//
//import org.springframework.web.bind.annotation.RequestAttribute;
//
//import com.transaction.book.entities.Rate;
//
//public interface MetalRateService {
//	
//	public Rate saveRateService(String metalName, double metalRate);
//	
//	public long totalDaysService(@RequestAttribute String startDate,@RequestAttribute String endDate);
//
//}
//


package com.transaction.book.services.serviceInterface;

import java.util.List;

import com.transaction.book.entities.Rate;

public interface MetalRateService {

	Rate saveRate(Double goldRate, Double silverRate);
    List<Rate> getLastThreeRates();
 

    long totalDaysService(String startDate, String endDate);
}


