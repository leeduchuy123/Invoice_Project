package com.invoice.Invoice_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String roleName;    //Chỉ lấy tên role

    public UserDTO(String username, String email, String roleName) {
        this.username = username;
        this.email = email;
        this.roleName = roleName;
    }
}
