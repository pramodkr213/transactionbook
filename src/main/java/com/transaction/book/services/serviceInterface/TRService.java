package com.transaction.book.services.serviceInterface;

import java.util.List;

import com.transaction.book.dto.responseDTO.TRResponseDTO;
import com.transaction.book.entities.TR;

public interface TRService {

    TRResponseDTO saveTR(TR tr, Long customerId);

    List<TRResponseDTO> getAllTR();

    TRResponseDTO getTRById(Integer id);

    List<TRResponseDTO> getTRByCustomerId(Long customerId);

    TRResponseDTO updateTR(Integer id, TR tr);

    String deleteTR(Integer id);
}


