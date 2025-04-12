package com.revie.apartments.auth.dto.response;

import com.revie.apartments.auth.dto.LoginDataDto;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record LoginResponseDto(
        HttpStatus status_code,
        String status,
        String message,
        LoginDataDto data
) {}
