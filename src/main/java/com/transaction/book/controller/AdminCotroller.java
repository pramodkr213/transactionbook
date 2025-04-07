package com.transaction.book.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.book.dto.responseObjects.DataResponse;
import com.transaction.book.dto.responseObjects.SuccessResponse;
import com.transaction.book.entities.User;
import com.transaction.book.services.serviceImpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@Slf4j
public class AdminCotroller {

    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @GetMapping("/allApprovalRequests")
    public ResponseEntity<DataResponse> allApprovalRequests(){
        log.info("Fetching all approval requests");
        DataResponse response = new DataResponse();
        try{
            response.setMessage("Approval requests get Successfully !");
            response.setData(this.userServiceImpl.getAllApprovalRequests());
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            log.info("Approval requests fetched successfully");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            log.error("Error fetching approval requests: {}", e.getMessage());
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/approveUser/{id}")
    public ResponseEntity<SuccessResponse> approveUser(@PathVariable("id")long id){
        log.info("Approving user with ID: {}", id);
        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserById(id);
        try{
            user.setApproved(true);
            this.userServiceImpl.registerUser(user);
            response.setMessage("Approve User Successfully !");
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            log.info("User with ID: {} approved successfully", id);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            log.error("Error approving user with ID: {}: {}", id, e.getMessage());
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteApprovalRequest/{id}")
    public ResponseEntity<SuccessResponse> deleteApprovalRequest(@PathVariable("id")long id){
        log.info("Deleting approval request for user with ID: {}", id);
        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserById(id);
        try{
            if(!user.isApproved()){
                this.userServiceImpl.deleteApprovalRequest(id);
                log.info("Approval request for user with ID: {} deleted successfully", id);
            }
            response.setMessage("delete request successfully Successfully !");
            response.setHttpStatus(HttpStatus.OK);
            response.setStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            log.error("Error deleting approval request for user with ID: {}: {}", id, e.getMessage());
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
