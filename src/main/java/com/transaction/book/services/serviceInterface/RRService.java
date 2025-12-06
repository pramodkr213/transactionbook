package com.transaction.book.services.serviceInterface;


import com.transaction.book.dto.responseDTO.RRResponseDTO;
import com.transaction.book.entities.RR;
import java.util.List;


public interface RRService {
RRResponseDTO saveRR(RR rr, Long customerId, Integer trId);
RRResponseDTO updateRR(Integer id, RR rr);
RRResponseDTO getRR(Integer id);
List<RRResponseDTO> listAll();
void deleteRR(Integer id);
}
