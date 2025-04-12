package com.revie.apartments.user.dto.response;

import com.revie.apartments.user.dto.DataDto;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SignUpResponseDto(
        HttpStatus status_code,
        String status,
        String message,
        DataDto data
) {
}
