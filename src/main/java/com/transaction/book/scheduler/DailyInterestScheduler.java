package com.transaction.book.scheduler;

import com.transaction.book.entities.InterestRecord;
import com.transaction.book.repository.InterestRecordRepo;
import com.transaction.book.services.serviceImpl.InterestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyInterestScheduler {

    private final InterestRecordRepo recordRepo;
    private final InterestService interestService;

    public DailyInterestScheduler(InterestRecordRepo recordRepo,
                                  InterestService interestService) {
        this.recordRepo = recordRepo;
        this.interestService = interestService;
    }

    // runs every day at 00:30 AM server time (configurable in application.properties)
    @Scheduled(cron = "${app.scheduler.cron:0 30 0 * * *}")
    @Transactional
    public void runDailyDeduction() {

        List<InterestRecord> records = recordRepo.findByFinishedFalse();

        LocalDate today = LocalDate.now();

        for (InterestRecord rec : records) {
            // avoid double-processing same day
            if (today.equals(rec.getLastProcessedDate())) continue;

            interestService.processOneDay(rec.getId());
        }
    }
}