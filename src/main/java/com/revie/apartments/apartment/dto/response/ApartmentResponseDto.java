package com.revie.apartments.apartment.dto.response;

import com.revie.apartments.apartment.dto.ApartmentDataDto;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApartmentResponseDto(
        HttpStatus status_code,
        String status,
        String message,
        ApartmentDataDto data
) {
}
