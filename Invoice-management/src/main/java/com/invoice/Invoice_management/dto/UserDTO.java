package com.invoice.Invoice_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String fullname;
    private String email;
    private String roleName;    //Chỉ lấy tên role

    public UserDTO(String fullname, String email, String roleName) {
        this.fullname = fullname;
        this.email = email;
        this.roleName = roleName;
    }
}
