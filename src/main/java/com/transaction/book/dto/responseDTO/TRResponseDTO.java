package com.transaction.book.dto.responseDTO;


import java.time.LocalDate;
import lombok.Data;

@Data
public class TRResponseDTO {

    private Integer id;
    private String trName;
    private String mediatorName;
    private LocalDate date;

    private long customerId;

    private String name;
    private String mobileNo;
    private double goldWgt;
    private double silverWgt;
    private String detail;
    private double goldTakenAmt;
    private double silverTakenAmt;
}


