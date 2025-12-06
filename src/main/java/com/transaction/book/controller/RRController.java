package com.transaction.book.controller;


import com.transaction.book.dto.responseDTO.ApiResponse;
import com.transaction.book.dto.responseDTO.RRResponseDTO;
import com.transaction.book.entities.RR;
import com.transaction.book.services.serviceInterface.RRService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/user/rr")
@CrossOrigin
public class RRController {


@Autowired
private RRService rrService;


@PostMapping("/add/{customerId}/{trId}")
public ResponseEntity<ApiResponse<RRResponseDTO>> addRR(
@PathVariable Long customerId,
@PathVariable Integer trId,
@RequestBody RR rr) {


RRResponseDTO saved = rrService.saveRR(rr, customerId, trId);
return ResponseEntity.ok(new ApiResponse<>(200, "RR created successfully", saved));
}


@GetMapping("/{id}")
public ResponseEntity<ApiResponse<RRResponseDTO>> getRR(@PathVariable Integer id) {
RRResponseDTO rr = rrService.getRR(id);
return ResponseEntity.ok(new ApiResponse<>(200, "RR fetched", rr));
}


@GetMapping
public ResponseEntity<ApiResponse<List<RRResponseDTO>>> listRR() {
List<RRResponseDTO> list = rrService.listAll();
return ResponseEntity.ok(new ApiResponse<>(200, "RR list", list));
}


@PutMapping("/{id}")
public ResponseEntity<ApiResponse<RRResponseDTO>> updateRR(
@PathVariable Integer id,
@RequestBody RR rr) {


RRResponseDTO updated = rrService.updateRR(id, rr);
return ResponseEntity.ok(new ApiResponse<>(200, "RR updated", updated));
}


@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<String>> deleteRR(@PathVariable Integer id) {
    rrService.deleteRR(id);
    return ResponseEntity.ok(new ApiResponse<>(200, "RR deleted", "RR deleted successfully"));
}


}