package com.transaction.book.services.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.book.dto.responseDTO.TRResponseDTO;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.TR;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.TRRepository;
import com.transaction.book.services.serviceInterface.TRService;

@Service
public class TRServiceImpl implements TRService {

    @Autowired
    private TRRepository trRepository;

    @Autowired
    private CustomerRepo customerRepository;

    // ✅ CREATE
    @Override
    public TRResponseDTO saveTR(TR tr, Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        tr.setCustomer(customer);

        // ✅ Copy customer snapshot to TR table
        tr.setName(customer.getName());
        tr.setMobileNo(customer.getMobileNo());
        tr.setGoldWgt(customer.getGoldWgt());
        tr.setSilverWgt(customer.getSilverWgt());
        tr.setDetail(customer.getDetail());
        tr.setGoldTakenAmt(customer.getGoldTakenAmt());
        tr.setSilverTakenAmt(customer.getSilverTakenAmt());

        TR saved = trRepository.save(tr);

        return mapToDTO(saved);
    }

    // ✅ GET ALL
    @Override
    public List<TRResponseDTO> getAllTR() {
        return trRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @Override
    public TRResponseDTO getTRById(Integer id) {
        TR tr = trRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TR not found"));

        return mapToDTO(tr);
    }

    // ✅ GET BY CUSTOMER ID
    @Override
    public List<TRResponseDTO> getTRByCustomerId(Long customerId) {
        return trRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ UPDATE TR
    @Override
    public TRResponseDTO updateTR(Integer id, TR trData) {

        TR tr = trRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TR not found"));

        tr.setTrName(trData.getTrName());
        tr.setMediatorName(trData.getMediatorName());
        tr.setDate(trData.getDate());

        TR updated = trRepository.save(tr);

        return mapToDTO(updated);
    }

    // ✅ DELETE TR
    @Override
    public String deleteTR(Integer id) {
        TR tr = trRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TR not found"));

        trRepository.delete(tr);

        return "TR deleted successfully";
    }

    // ✅ DTO Mapper
    private TRResponseDTO mapToDTO(TR tr) {
        TRResponseDTO dto = new TRResponseDTO();

        dto.setId(tr.getId());
        dto.setTrName(tr.getTrName());
        dto.setMediatorName(tr.getMediatorName());
        dto.setDate(tr.getDate());
        dto.setCustomerId(tr.getCustomer().getId());

        dto.setName(tr.getName());
        dto.setMobileNo(tr.getMobileNo());
        dto.setGoldWgt(tr.getGoldWgt());
        dto.setSilverWgt(tr.getSilverWgt());
        dto.setDetail(tr.getDetail());
        dto.setGoldTakenAmt(tr.getGoldTakenAmt());
        dto.setSilverTakenAmt(tr.getSilverTakenAmt());

        return dto;
    }
}
