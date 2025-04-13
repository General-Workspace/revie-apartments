package com.revie.apartments.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revie.apartments.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequestDto(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20)
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @JsonProperty("first_name")
        @NotBlank(message = "First name is required")
        String firstName,

        @JsonProperty("last_name")
        @NotBlank(message = "Last name is required")
        String lastName,

        @JsonProperty("user_role")
        UserRole userRole
) {
        public UserRole resolvedUserRole() {
                return userRole != null ? userRole : UserRole.USER;
        }
}
