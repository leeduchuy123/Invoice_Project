package com.invoice.Invoice_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name= "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String description;
    //Admin: full control
    //User: Create invoice only

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;
}
