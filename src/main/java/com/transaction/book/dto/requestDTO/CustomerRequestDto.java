package com.transaction.book.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDto {

    @NotBlank(message = "Name is required")
//    @NotNull("Name should not null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
    private String mobileNo;

//    @Pattern(regexp = "^[0-9A-Z]{15}$", message = "Invalid GSTIN format")
//    private String gstinNo;

//    private String reference;

//    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String date;

    private boolean gave;
    private boolean got;

    private AddressRequest address;
    
//    @DecimalMin(value = "0.0", inclusive = false, message = "Gold Selling Amount must be greater than 0")
    private double goldSellingAmt;
//    @DecimalMin(value = "0.0", inclusive = false, message = "Gold taken Amount must be greater than 0")
    private double goldTakenAmt;
    
    private double goldWgt;
//    @DecimalMin(value = "0.0", inclusive = false, message = "Silver Selling Amount must be greater than 0")
    private double silverSellingAmt;
    
//    @DecimalMin(value = "0.0", inclusive = false, message = "silverTakenAmt Amount must be greater than 0")
    private double silverTakenAmt;
    
//    @DecimalMin(value = "0.0", inclusive = false, message = "Silver Wight must be greater than 0")
    private double silverWgt;
    private double interest;
    private String detail;
}
