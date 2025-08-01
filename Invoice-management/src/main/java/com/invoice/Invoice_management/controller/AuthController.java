package com.invoice.Invoice_management.controller;

import com.invoice.Invoice_management.dto.UserDTO;
import com.invoice.Invoice_management.entity.CustomUserDetails;
import com.invoice.Invoice_management.entity.Role;
import com.invoice.Invoice_management.entity.User;
import com.invoice.Invoice_management.jwt.JwtTokenProvider;
import com.invoice.Invoice_management.payload.LoginRequest;
import com.invoice.Invoice_management.payload.AuthResponse;
import com.invoice.Invoice_management.payload.RegisterRequest;
import com.invoice.Invoice_management.repository.RoleRepository;
import com.invoice.Invoice_management.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity(prePostEnabled = true)
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        //Mac dinh Role là "USER"
        Role userRole = roleRepository.findByRoleName("USER");

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(userRole);

        User savedUser = userRepository.save(user);

        UserDTO response = new UserDTO(savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole().getRoleName());

        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();    // 🕒 Kết thúc đo thời gian
        System.out.println("Register API duration: " + (end - start) + " ms");

        return ResponseEntity.ok(response);
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public String randomStuff(){
        System.out.println("JWT Hợp lệ mới có thể thấy được message này");

        return "";
    }
}
