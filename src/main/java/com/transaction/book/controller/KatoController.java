package com.transaction.book.controller;

import com.transaction.book.dto.responseDTO.ApiResponse;
import com.transaction.book.dto.responseDTO.KatoResponseDTO;
import com.transaction.book.entities.Kato;
import com.transaction.book.services.serviceInterface.KatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/kato")
@CrossOrigin
public class KatoController {

    @Autowired
    private KatoService katoService;

    @PostMapping("/add/{customerId}")
    public ResponseEntity<ApiResponse<KatoResponseDTO>> addKato(
            @PathVariable Long customerId,
            @RequestBody Kato kato) {

        KatoResponseDTO saved = katoService.saveKato(kato, customerId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Kato created successfully", saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KatoResponseDTO>> getKato(@PathVariable Integer id) {
        KatoResponseDTO kato = katoService.getKato(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Kato fetched", kato));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<KatoResponseDTO>>> listKato() {
        List<KatoResponseDTO> list = katoService.listAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Kato list", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KatoResponseDTO>> updateKato(
            @PathVariable Integer id,
            @RequestBody Kato kato) {

        KatoResponseDTO updated = katoService.updateKato(id, kato);
        return ResponseEntity.ok(new ApiResponse<>(200, "Kato updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteKato(@PathVariable Integer id) {
        katoService.deleteKato(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Kato deleted", "Kato deleted successfully"));
    }
}