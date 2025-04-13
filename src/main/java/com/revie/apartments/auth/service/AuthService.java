package com.revie.apartments.auth.service;

import com.revie.apartments.auth.dto.LoginDataDto;
import com.revie.apartments.auth.dto.request.LoginRequestDto;
import com.revie.apartments.auth.dto.response.LoginResponseDto;
import com.revie.apartments.exceptions.UnauthorizedException;
import com.revie.apartments.user.entity.User;
import com.revie.apartments.user.repository.UserRepository;
import com.revie.apartments.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public LoginResponseDto authenticate(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByUsername(request.username())
                    .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

            String access_token = jwtTokenProvider.generateToken(authentication);

            return LoginResponseDto.builder()
                    .status_code(HttpStatus.OK)
                    .status("success")
                    .message("Login successful")
                    .data(
                            LoginDataDto.builder()
                                    .id(user.getId())
                                    .username(user.getUsername())
                                    .email(user.getEmail())
                                    .first_name(user.getFirstName())
                                    .last_name(user.getLastName())
                                    .created_at(user.getCreatedAt())
                                    .updated_at(user.getUpdatedAt())
                                    .access_token(access_token)
                                    .build()
                    )
                    .build();
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}
