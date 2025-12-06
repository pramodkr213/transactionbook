package com.transaction.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.book.entities.Rate;
import com.transaction.book.services.serviceInterface.MetalRateService;

@RestController
@RequestMapping("/rate")
@CrossOrigin
public class RateController {

    private static final Logger logger = LogManager.getLogger(RateController.class);

    @Autowired
    private MetalRateService metalRateService;

    // ✅ Single Metal Rate Update API
   
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateRate(
            @RequestParam Double goldRate,
            @RequestParam Double silverRate) {

        Rate savedRate = metalRateService.saveRate(goldRate, silverRate);
        List<Rate> lastThree = metalRateService.getLastThreeRates();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Rates updated successfully");
        response.put("currentRate", savedRate);
        response.put("lastThreeRates", lastThree);

        return ResponseEntity.ok(response);
    }

    // ✅ Get last 3 rates only
    @GetMapping("/latest")
    public ResponseEntity<List<Rate>> getLastThreeRates() {
        return ResponseEntity.ok(metalRateService.getLastThreeRates());
    }

}
