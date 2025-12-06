package com.transaction.book.dto.requestDTO;

import java.util.Date;
import lombok.Data;

@Data
public class TransferReceiptRequest {
    private Long customerId;
    private String TRName;
    private String MediatorName;
    private Date TRDate;
}
