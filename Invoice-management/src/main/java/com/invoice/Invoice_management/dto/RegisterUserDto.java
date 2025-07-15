package com.invoice.Invoice_management.dto;

import com.invoice.Invoice_management.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullname;

    public RegisterUserDto(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
}
