package com.revie.apartments.reviews.dto;

import com.revie.apartments.user.enums.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String username,
        String email,
        String first_name,
        String last_name,
        UserRole user_role
) {
}
