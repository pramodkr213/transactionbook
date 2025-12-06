package com.transaction.book.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.book.dto.requestDTO.HistryPaymentRequest;
import com.transaction.book.dto.responseDTO.Dashboard;
import com.transaction.book.dto.responseObjects.DataResponse;
import com.transaction.book.dto.responseObjects.SuccessResponse;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.HistryPayment;
import com.transaction.book.entities.User;
import com.transaction.book.repository.HistryPaymentRepository;
import com.transaction.book.services.serviceImpl.CustomerServiceImpl;
import com.transaction.book.services.serviceImpl.UserServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;
    @Autowired
    private HistryPaymentRepository histryPaymentRepository;

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String jwt) {
        log.info("Fetching profile for JWT: {}", jwt);
        try {
            DataResponse response = new DataResponse();
            response.setData(this.userServiceImpl.getUserByJwt(jwt));
            log.info("User profile fetched successfully for JWT: {}", jwt);
            response.setMessage("User profile get successfully !");
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            log.error("Error fetching profile for JWT: {}: {}", jwt, e.getMessage());
            SuccessResponse response = new SuccessResponse();
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getDashboard")
    public ResponseEntity<Object> getDashboard() {
        log.info("Fetching dashboard data");
        try {
            Dashboard dashboard = new Dashboard();
            try {
                dashboard.setYouWillGet(this.customerServiceImpl.getTotalGetAmount());
            } catch (Exception e) {
                log.warn("Error fetching 'You Will Get' amount: {}", e.getMessage());
                dashboard.setYouWillGet(0);
            }
            try {
                dashboard.setYouWillGave(this.customerServiceImpl.getToalGaveAmount());
            } catch (Exception e) {
                log.warn("Error fetching 'You Will Gave' amount: {}", e.getMessage());
                dashboard.setYouWillGave(0);
            }
            DataResponse response = new DataResponse();
            response.setData(dashboard);
            log.info("Dashboard data fetched successfully");
            response.setMessage("Dashboard get successfully !");
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            log.error("Error fetching dashboard data: {}", e.getMessage());
            SuccessResponse response = new SuccessResponse();
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/sendFCMToken")
    public ResponseEntity<SuccessResponse> setFCMToken(@RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String token, @RequestParam(required = false) boolean web) {
        log.info("Setting FCM token for JWT: {}", jwt);
        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserByJwt(jwt);
        try {
            if (token == null) {
                log.warn("FCM token is null for JWT: {}", jwt);
                response.setMessage("something went wrong !");
                response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setStatusCode(500);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            if (web) {
                user.setFcmTokenWeb(token);
            } else {
                user.setFcmToken(token);
            }
            this.userServiceImpl.registerUser(user);

            log.info("FCM token set successfully for JWT: {}", jwt);
            response.setMessage("User profile get successfully !");
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            log.error("Error setting FCM token for JWT: {}: {}", jwt, e.getMessage());
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getCustomerByMobile/{mobileNO}")
    public ResponseEntity<?> getCustomerNumber(@PathVariable("mobileNO") String mobileNo) {
        DataResponse response = new DataResponse();
        try {
            Customer customer = this.customerServiceImpl.getCustomerByMobileNo(mobileNo);
            if (customer == null) {
                response.setMessage("Customer not found !");
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setStatusCode(404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.setMessage("Customer found successfully !");
                response.setHttpStatus(HttpStatus.OK);
                response.setStatusCode(200);
                response.setData(customer);
                return ResponseEntity.of(Optional.of(response));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/history/{customerId}")
    public List<HistryPayment> getPaymentHistory(@PathVariable Long customerId) {
        return histryPaymentRepository.findByCustomerId(customerId);
    }

//     @PostMapping("/add/history")
// public void addEntities(@RequestBody List<HistryPayment> payments) {
//     histryPaymentRepository.saveAll(payments);
// }

 @PostMapping("/add/history")
public ResponseEntity<DataResponse> addEntities(@RequestBody HistryPaymentRequest request) {
     histryPaymentRepository.saveAll(request.getHistoryPayments());

    return ResponseEntity.ok().body(new DataResponse() {{
        setMessage("History payments added successfully!");
        setHttpStatus(HttpStatus.OK);
        setStatusCode(200);
        setData(request.getHistoryPayments());
    }});
}
}