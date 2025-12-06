package com.transaction.book.dto.responseDTO;

import java.time.LocalDate;
import lombok.Data;


@Data
public class RRResponseDTO {
private Integer id;
private String mediatorName;
private LocalDate date;


private String trName;


private String name;
private String mobileNo;
private double goldWgt;
private double silverWgt;
private String detail;
private double goldTakenAmt;
private double silverTakenAmt;


private long customerId;
private int trId;
}
