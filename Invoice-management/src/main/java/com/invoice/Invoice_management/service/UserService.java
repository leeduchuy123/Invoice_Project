package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.UserDTO;
import com.invoice.Invoice_management.entity.User;
import com.invoice.Invoice_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //Helper method to convert Entity <-> DTO
    private UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getFullname(), user.getEmail(), user.getRole().getRoleName());
        userDTO.setId(user.getId());
        return userDTO;
    }

    //Get User by Token
    public List<UserDTO> getUserByToken(String token) {
        List<User> users = userRepository.findUsersByToken(token);
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}