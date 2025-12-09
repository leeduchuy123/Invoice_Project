package com.invoice.Invoice_management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String password;
}
