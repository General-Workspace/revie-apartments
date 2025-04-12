package com.revie.apartments.user.service;

import com.revie.apartments.exceptions.BadRequestException;
import com.revie.apartments.exceptions.ConflictException;
import com.revie.apartments.user.dto.DataDto;
import com.revie.apartments.user.dto.request.SignUpRequestDto;
import com.revie.apartments.user.dto.response.SignUpResponseDto;
import com.revie.apartments.user.entity.User;
import com.revie.apartments.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadRequestException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

    /**
     * Registers a new user in the system.
     *
     * @param request The sign-up request containing user details.
     * @return A response DTO containing the status and user data.
     * @throws ConflictException if the username or email is already taken.
     */

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("Username " + request.username() + " is already taken.");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email " + request.email() + " is already registered.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        userRepository.save(user);

        return SignUpResponseDto.builder().
                status_code(HttpStatus.CREATED).
                status("success").
                message("User registered successfully").
                data(DataDto.builder().
                        id(user.getId()).
                        username(user.getUsername()).
                        email(user.getEmail()).
                        first_name(user.getFirstName()).
                        last_name(user.getLastName()).
                        created_at(user.getCreatedAt()).
                        updated_at(user.getUpdatedAt())
                        .build())
                        .build();
    }
}
