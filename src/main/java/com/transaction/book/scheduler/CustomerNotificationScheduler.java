package com.transaction.book.scheduler;

import com.transaction.book.entities.Customer;
import com.transaction.book.entities.CustomerNotification;
import com.transaction.book.repository.CustomerNotificationRepo;
import com.transaction.book.services.serviceImpl.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class CustomerNotificationScheduler {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private CustomerNotificationRepo customerNotificationRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * This method runs every day at 10:00 AM
     * and checks for customers who completed 180 days since updateDate.
     */
    @Scheduled(cron = "0 0 10 * * *") // Every day 10 AM
    public void send180DaysNotification() {
        log.info("🔍 Checking customers for 180-day notifications...");

        List<Customer> customers = customerServiceImpl.getAllCustomers(); // must exist in your service

        LocalDate today = LocalDate.now();

        for (Customer customer : customers) {
            try {
                if (customer.getUpdateDate() == null)
                    continue;

                LocalDate updateDate = LocalDate.parse(customer.getUpdateDate().substring(0, 10), formatter);
                LocalDate notificationDate = updateDate.plusDays(180);

                if (today.isEqual(notificationDate)) {
                    String msg = String.format(
                            "Customer '%s' (%s) completed 180 days since %s.",
                            customer.getName(), customer.getMobileNo(), updateDate);

                    // Save notification in DB
                    CustomerNotification notif = new CustomerNotification();
                    notif.setCustomerId(customer.getId());
                    notif.setCustomerName(customer.getName());
                    notif.setMobileNo(customer.getMobileNo());
                    notif.setNotifiedDate(today);
                    notif.setMessage(msg);
                    notif.setSent(true);

                    customerNotificationRepo.save(notif);

                    // Just console output for now (can be replaced by SMS/Email)
                    log.info("🔔 Notification sent: {}", msg);
                }
            } catch (Exception e) {
                log.error("Error checking customer {}: {}", customer.getName(), e.getMessage());
            }
        }

        log.info("✅ 180-day notification check complete.");
    }
}

// package com.transaction.book.scheduler;

// import com.transaction.book.entities.Customer;
// import com.transaction.book.entities.CustomerNotification;
// import com.transaction.book.repository.CustomerNotificationRepo;
// import com.transaction.book.services.serviceImpl.CustomerServiceImpl;

// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.List;

// @Component
// @Slf4j
// public class CustomerNotificationScheduler {

//     @Autowired
//     private CustomerServiceImpl customerServiceImpl;

//     @Autowired
//     private CustomerNotificationRepo customerNotificationRepo;

//     private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//     /**
//      * Runs automatically every 25 minutes.
//      */
//     @Scheduled(fixedRate = 1500000) // 25 minutes = 1,500,000 ms
//     public void send180DaysNotification() {
//         log.info("🔍 Running 180-day notification scheduler...");

//         List<Customer> customers = customerServiceImpl.getAllCustomers();
//         LocalDate today = LocalDate.now();

//         for (Customer customer : customers) {

//             try {

//                 if (customer.getUpdateDate() == null) {
//                     continue;
//                 }

//                 // Parse date safely
//                 String dateOnly = customer.getUpdateDate().substring(0, 10);
//                 LocalDate updateDate = LocalDate.parse(dateOnly, formatter);

//                 // Add 180 days
//                 LocalDate notificationDate = updateDate.plusDays(180);

//                 // Match today
//                 if (today.isEqual(notificationDate)) {

//                     String msg = String.format(
//                             "Customer '%s' (%s) completed 180 days since %s.",
//                             customer.getName(),
//                             customer.getMobileNo(),
//                             updateDate);

//                     // Save notification
//                     CustomerNotification notif = new CustomerNotification();
//                     notif.setCustomerId(customer.getId());
//                     notif.setCustomerName(customer.getName());
//                     notif.setMobileNo(customer.getMobileNo());
//                     notif.setNotifiedDate(today);
//                     notif.setMessage(msg);
//                     notif.setSent(true);

//                     customerNotificationRepo.save(notif);

//                     log.info("🔔 Notification saved: {}", msg);
//                 }

//             } catch (Exception e) {
//                 log.error("❌ Error processing customer {}: {}", customer.getName(), e.getMessage());
//             }
//         }

//         log.info("✅ Scheduler finished.");
//     }
// }
