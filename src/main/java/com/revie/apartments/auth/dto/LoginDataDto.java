package com.revie.apartments.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginDataDto(
        String id,
        String username,
        String email,
        String first_name,
        String last_name,
        String access_token,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
