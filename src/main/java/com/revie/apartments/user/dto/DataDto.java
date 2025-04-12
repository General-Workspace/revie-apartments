package com.revie.apartments.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DataDto(
        String id,
        String username,
        String email,
        String first_name,
        String last_name,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
