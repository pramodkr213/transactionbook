package com.transaction.book.services.serviceImpl;

import com.transaction.book.entities.Customer;
import com.transaction.book.entities.InterestRecord;
import com.transaction.book.entities.Notification;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.InterestRecordRepo;
import com.transaction.book.repository.NotificationRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class InterestService {

    private final CustomerRepo customerRepo;
    private final InterestRecordRepo recordRepo;
    private final NotificationRepo notificationRepo;

    public InterestService(CustomerRepo customerRepo,
                           InterestRecordRepo recordRepo,
                           NotificationRepo notificationRepo) {
        this.customerRepo = customerRepo;
        this.recordRepo = recordRepo;
        this.notificationRepo = notificationRepo;
    }

    private static final int SCALE = 2;
    private static final BigDecimal DAYS_IN_YEAR = new BigDecimal("360");

    // create records for both GOLD and SILVER if amounts > 0
    @Transactional
    public void startAllRecordsForCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        BigDecimal interestPercent = BigDecimal.valueOf(customer.getInterest());

        if (customer.getGoldTakenAmt() > 0) {
            createRecord(customer, "GOLD", BigDecimal.valueOf(customer.getGoldTakenAmt()), interestPercent);
        }

        if (customer.getSilverTakenAmt() > 0) {
            createRecord(customer, "SILVER", BigDecimal.valueOf(customer.getSilverTakenAmt()), interestPercent);
        }
    }

    // helper: create a single record
    @Transactional
    public InterestRecord createRecord(Customer customer, String metalType, BigDecimal amount, BigDecimal interestPercent) {

        amount = amount.setScale(SCALE, RoundingMode.HALF_UP);

        // cut 25%
        BigDecimal cutAmount = amount.multiply(new BigDecimal("0.25")).setScale(SCALE, RoundingMode.HALF_UP);

        // internal 75% of cut
        BigDecimal internal75 = cutAmount.multiply(new BigDecimal("0.75")).setScale(SCALE, RoundingMode.HALF_UP);

        // daily interest = (amount * (interest/100)) / 360
        BigDecimal dailyInterest = amount
                .multiply(interestPercent)
                .divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
                .divide(DAYS_IN_YEAR, SCALE + 2, RoundingMode.HALF_UP)
                .setScale(SCALE, RoundingMode.HALF_UP);

        InterestRecord rec = new InterestRecord();
        rec.setCustomer(customer);
        rec.setMetalType(metalType);
        rec.setOriginalAmount(amount);
        rec.setCutAmount(cutAmount);
        rec.setInternal75(internal75);
        rec.setRemainingInternal(internal75);
        rec.setDailyInterest(dailyInterest);
        rec.setFinished(false);
        rec.setCreatedAt(LocalDate.now());
        rec.setLastProcessedDate(null);

        return recordRepo.save(rec);
    }

    // process one day for the given record (manual/test)
    @Transactional
    public InterestRecord processOneDay(Long recordId) {
        InterestRecord rec = recordRepo.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("InterestRecord not found: " + recordId));
        if (rec.isFinished()) return rec;

        BigDecimal remaining = rec.getRemainingInternal();
        BigDecimal daily = rec.getDailyInterest();

        BigDecimal newRemaining = remaining.subtract(daily).max(BigDecimal.ZERO).setScale(SCALE, RoundingMode.HALF_UP);
        rec.setRemainingInternal(newRemaining);
        rec.setLastProcessedDate(LocalDate.now());

        if (newRemaining.compareTo(BigDecimal.ZERO) <= 0) {
            rec.setFinished(true);
            createNotificationFor(rec.getCustomer(), rec.getMetalType());
        }

        return recordRepo.save(rec);
    }

    private void createNotificationFor(Customer customer, String metalType) {
        Notification n = new Notification();
        n.setCustomerId(customer.getId());
        n.setMetalType(metalType);
        n.setMessage(buildNotification(customer.getName(), customer.getMobileNo(), metalType));
        n.setCreatedAt(LocalDateTime.now());
        notificationRepo.save(n);

        // TODO: integrate real SMS/Email sender here if required
    }

    private String buildNotification(String name, String mobile, String metalType) {
        return "Alert: Internal reserve for customer " + name
                + " (Mobile: " + mobile + ") for " + metalType
                + " is exhausted. Please initiate follow-up.";
    }
}