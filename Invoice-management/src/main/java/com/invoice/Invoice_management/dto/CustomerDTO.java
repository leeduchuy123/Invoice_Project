package com.invoice.Invoice_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String fullname;
    private String address;
    private String phoneNumber;
    private String email;

    public CustomerDTO(String fullname, String address, String phoneNumber, String email) {
        this.fullname = fullname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
