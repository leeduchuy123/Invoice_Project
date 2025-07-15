package com.invoice.Invoice_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Thêm prefix ROLE_ theo chuẩn của Spring Security
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String fullname, String password, String email, Role role) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
