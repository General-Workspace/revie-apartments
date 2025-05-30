package com.revie.apartments.user.dto;

import com.revie.apartments.user.enums.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DataDto(
        String id,
        String username,
        String email,
        String first_name,
        String last_name,
        UserRole user_role,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
