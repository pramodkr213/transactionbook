package com.transaction.book.services.serviceInterface;

import com.transaction.book.dto.responseDTO.KatoResponseDTO;
import com.transaction.book.entities.Kato;
import java.util.List;

public interface KatoService {
    KatoResponseDTO saveKato(Kato kato, Long customerId);
    KatoResponseDTO updateKato(Integer id, Kato kato);
    KatoResponseDTO getKato(Integer id);
    List<KatoResponseDTO> listAll();
    void deleteKato(Integer id);
}