package com.transaction.book.controller;

import com.transaction.book.entities.InterestRecord;
import com.transaction.book.entities.Notification;
import com.transaction.book.repository.InterestRecordRepo;
import com.transaction.book.repository.NotificationRepo;
import com.transaction.book.services.serviceImpl.InterestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest")
public class InterestController {

    private final InterestService interestService;
    private final InterestRecordRepo recordRepo;
    private final NotificationRepo notificationRepo;

    public InterestController(InterestService interestService,
                              InterestRecordRepo recordRepo,
                              NotificationRepo notificationRepo) {
        this.interestService = interestService;
        this.recordRepo = recordRepo;
        this.notificationRepo = notificationRepo;
    }

    // Create start records for both GOLD & SILVER for a customer
    @PostMapping("/start/{customerId}")
    public List<InterestRecord> start(@PathVariable Long customerId) {
        interestService.startAllRecordsForCustomer(customerId);
        return recordRepo.findByFinishedFalse();
    }

    // Manual one-day process (testing)
    @PostMapping("/process/{recordId}")
    public InterestRecord processOneDay(@PathVariable Long recordId) {
        return interestService.processOneDay(recordId);
    }

    @GetMapping("/records")
    public List<InterestRecord> listRecords() {
        return recordRepo.findAll();
    }

    @GetMapping("/notifications")
    public List<Notification> listNotifications() {
        return notificationRepo.findAll();
    }
}