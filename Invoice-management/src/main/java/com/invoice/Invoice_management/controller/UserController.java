package com.invoice.Invoice_management.controller;

import com.invoice.Invoice_management.dto.UserDTO;
import com.invoice.Invoice_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/find/{token}")
    public List<UserDTO> findUsersByToken(@PathVariable String token) {
        return userService.findUsersByToken(token);
    }
}
