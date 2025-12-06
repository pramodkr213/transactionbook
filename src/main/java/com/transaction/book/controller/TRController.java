package com.transaction.book.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.transaction.book.dto.responseDTO.ApiResponse;
import com.transaction.book.dto.responseDTO.TRResponseDTO;
import com.transaction.book.entities.TR;
import com.transaction.book.services.serviceInterface.TRService;

@RestController
@RequestMapping("/api/user/tr")
@CrossOrigin
public class TRController {

    @Autowired
    private TRService trService;

    // ✅ CREATE TR
    @PostMapping("/add/{customerId}")
    public ResponseEntity<ApiResponse<TRResponseDTO>> addTR(
            @PathVariable Long customerId,
            @RequestBody TR tr) {

        TRResponseDTO saved = trService.saveTR(tr, customerId);

        return ResponseEntity.ok(new ApiResponse<>(200, "TR created successfully", saved));
    }

    // ✅ GET ALL TR
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TRResponseDTO>>> getAllTR() {
        return ResponseEntity.ok(new ApiResponse<>(200, "All TR fetched", trService.getAllTR()));
    }

    // ✅ GET SINGLE TR
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TRResponseDTO>> getTRById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "TR fetched", trService.getTRById(id)));
    }

    // ✅ GET BY CUSTOMER
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<TRResponseDTO>>> getTRByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(new ApiResponse<>(200, "TR by customer", trService.getTRByCustomerId(customerId)));
    }

    // ✅ UPDATE TR
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TRResponseDTO>> updateTR(
            @PathVariable Integer id,
            @RequestBody TR tr) {

        TRResponseDTO updated = trService.updateTR(id, tr);

        return ResponseEntity.ok(new ApiResponse<>(200, "TR updated", updated));
    }

    // ✅ DELETE TR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTR(@PathVariable Integer id) {
        String msg = trService.deleteTR(id);
        return ResponseEntity.ok(new ApiResponse<>(200, msg, null));
    }
}

