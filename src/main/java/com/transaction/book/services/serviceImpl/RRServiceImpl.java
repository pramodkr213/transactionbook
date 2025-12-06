package com.transaction.book.services.serviceImpl;


import com.transaction.book.dto.responseDTO.RRResponseDTO;
import com.transaction.book.entities.Customer;
import com.transaction.book.entities.RR;
import com.transaction.book.entities.TR;
import com.transaction.book.repository.CustomerRepo;
import com.transaction.book.repository.RRRepository;
import com.transaction.book.repository.TRRepository;
import com.transaction.book.services.serviceInterface.RRService;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class RRServiceImpl implements RRService {


private final RRRepository rrRepository;
private final CustomerRepo customerRepository;
private final TRRepository trRepository;


public RRServiceImpl(RRRepository rrRepository, CustomerRepo customerRepository, TRRepository trRepository) {
this.rrRepository = rrRepository;
this.customerRepository = customerRepository;
this.trRepository = trRepository;
}


@Override
public RRResponseDTO saveRR(RR rr, Long customerId, Integer trId) {


Customer customer = customerRepository.findById(customerId)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));


TR tr = trRepository.findById(trId)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TR not found"));


rr.setCustomer(customer);
rr.setTr(tr);


rr.setName(customer.getName());
rr.setMobileNo(customer.getMobileNo());
rr.setGoldWgt(customer.getGoldWgt());
rr.setSilverWgt(customer.getSilverWgt());
rr.setDetail(customer.getDetail());
rr.setGoldTakenAmt(customer.getGoldTakenAmt());
rr.setSilverTakenAmt(customer.getSilverTakenAmt());


rr.setTrName(tr.getTrName());


RR saved = rrRepository.save(rr);
return mapToDTO(saved);
}


@Override
public RRResponseDTO updateRR(Integer id, RR payload) {
RR existing = rrRepository.findById(id)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RR not found"));


if (payload.getMediatorName() != null)
existing.setMediatorName(payload.getMediatorName());


if (payload.getDate() != null)
existing.setDate(payload.getDate());


RR updated = rrRepository.save(existing);
return mapToDTO(updated);
}


@Override
public RRResponseDTO getRR(Integer id) {
RR rr = rrRepository.findById(id)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RR not found"));
return mapToDTO(rr);
}


@Override
public List<RRResponseDTO> listAll() {
return rrRepository.findAll()
.stream()
.map(this::mapToDTO)
.collect(Collectors.toList());
}


@Override
public void deleteRR(Integer id) {
RR rr = rrRepository.findById(id)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RR not found"));
rrRepository.delete(rr);
}


private RRResponseDTO mapToDTO(RR rr) {
RRResponseDTO dto = new RRResponseDTO();


dto.setId(rr.getId());
dto.setMediatorName(rr.getMediatorName());
dto.setDate(rr.getDate());


dto.setTrName(rr.getTrName());


dto.setName(rr.getName());
dto.setMobileNo(rr.getMobileNo());
dto.setGoldWgt(rr.getGoldWgt());
dto.setSilverWgt(rr.getSilverWgt());
dto.setDetail(rr.getDetail());
dto.setGoldTakenAmt(rr.getGoldTakenAmt());
dto.setSilverTakenAmt(rr.getSilverTakenAmt());


dto.setCustomerId(rr.getCustomer().getId());
dto.setTrId(rr.getTr().getId());


return dto;
}
}