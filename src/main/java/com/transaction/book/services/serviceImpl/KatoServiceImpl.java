package com.transaction.book.services.serviceImpl;

import com.transaction.book.dto.responseDTO.KatoResponseDTO;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.Kato;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.KatoRepository;
import com.transaction.book.services.serviceInterface.KatoService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KatoServiceImpl implements KatoService {

    private final KatoRepository katoRepository;
    private final CustomerRepo customerRepository;

    public KatoServiceImpl(KatoRepository katoRepository, CustomerRepo customerRepository) {
        this.katoRepository = katoRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public KatoResponseDTO saveKato(Kato kato, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        kato.setCustomer(customer);

        // copy fields from customer
        kato.setName(customer.getName());
        kato.setMobileNo(customer.getMobileNo());
        kato.setGoldWgt(customer.getGoldWgt());
        kato.setSilverWgt(customer.getSilverWgt());
        kato.setDetail(customer.getDetail());
        kato.setGoldTakenAmt(customer.getGoldTakenAmt());
        kato.setSilverTakenAmt(customer.getSilverTakenAmt());
        kato.setUpdateDate(customer.getUpdateDate());

        Kato saved = katoRepository.save(kato);
        return mapToDTO(saved);
    }

    @Override
    public KatoResponseDTO updateKato(Integer id, Kato payload) {
        Kato existing = katoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kato not found"));

        if (payload.getReceiverName() != null)
            existing.setReceiverName(payload.getReceiverName());

        if (payload.getDate() != null)
            existing.setDate(payload.getDate());

        Kato updated = katoRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public KatoResponseDTO getKato(Integer id) {
        Kato kato = katoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kato not found"));
        return mapToDTO(kato);
    }

    @Override
    public List<KatoResponseDTO> listAll() {
        return katoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteKato(Integer id) {
        Kato kato = katoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kato not found"));
        katoRepository.delete(kato);
    }

    private KatoResponseDTO mapToDTO(Kato kato) {
        KatoResponseDTO dto = new KatoResponseDTO();
        dto.setId(kato.getId());
        dto.setDate(kato.getDate());
        dto.setReceiverName(kato.getReceiverName());
        dto.setName(kato.getName());
        dto.setMobileNo(kato.getMobileNo());
        dto.setGoldWgt(kato.getGoldWgt());
        dto.setSilverWgt(kato.getSilverWgt());
        dto.setDetail(kato.getDetail());
        dto.setGoldTakenAmt(kato.getGoldTakenAmt());
        dto.setSilverTakenAmt(kato.getSilverTakenAmt());
        dto.setUpdateDate(kato.getUpdateDate());
        dto.setCustomerId(kato.getCustomer().getId());
        return dto;
    }
}