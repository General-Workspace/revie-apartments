package com.revie.apartments.apartment.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApartmentDataDto(
        String id,
        String address,
        String city,
        String state,
        String country,
        String zip_code,
        String user_id,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {}
