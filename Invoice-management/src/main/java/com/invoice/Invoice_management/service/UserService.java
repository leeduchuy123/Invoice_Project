package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.UserDTO;
import com.invoice.Invoice_management.entity.CustomUserDetails;
import com.invoice.Invoice_management.entity.User;
import com.invoice.Invoice_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName()
        );
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return new CustomUserDetails(user);
    }

    //Hanlde User's module
    public List<UserDTO> findUsersByToken(String token) {
        List<User> users = userRepository.findUsersByToken(token);
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

     public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::toDTO).collect(Collectors.toList());
     }
}