package com.transaction.book.controller;

import com.transaction.book.entities.CustomerNotification;
import com.transaction.book.repository.CustomerNotificationRepo;
import com.transaction.book.dto.responseObjects.DataResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/notifications")
@CrossOrigin
public class CustomerNotificationController {

    @Autowired
    private CustomerNotificationRepo customerNotificationRepo;

    @GetMapping("/all")
    public ResponseEntity<DataResponse> getAllNotifications() {
        DataResponse response = new DataResponse();
        List<CustomerNotification> notifications = customerNotificationRepo.findAll();
        response.setData(notifications);
        response.setMessage("Fetched all customer notifications successfully!");
        response.setHttpStatus(HttpStatus.OK);
        response.setStatusCode(200);
        return ResponseEntity.of(Optional.of(response));
    }
}