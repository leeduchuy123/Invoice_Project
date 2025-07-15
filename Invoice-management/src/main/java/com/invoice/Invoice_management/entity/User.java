package com.invoice.Invoice_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @NotNull
    @Size(min=6, message = "Password must be at least 6 characters")
    private String password;

    @Email(message = "Email is not validation", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //mappedBy = user: khoa ngoai nam o phia Order, thuoc tinh "user" la noi dinh nghia quan he nay
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
