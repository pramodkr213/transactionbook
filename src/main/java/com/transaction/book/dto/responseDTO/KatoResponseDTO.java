package com.transaction.book.dto.responseDTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class KatoResponseDTO {
    private Integer id;
    private LocalDate date;
    private String receiverName;

    private String name;
    private String mobileNo;
    private double goldWgt;
    private double silverWgt;
    private String detail;
    private double goldTakenAmt;
    private double silverTakenAmt;
    private String updateDate;

    private long customerId;

	
}