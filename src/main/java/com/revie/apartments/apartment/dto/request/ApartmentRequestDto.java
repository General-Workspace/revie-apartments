package com.revie.apartments.apartment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ApartmentRequestDto(
        @NotBlank(message = "Provide address of the apartment")
        String address,

        @NotBlank(message = "Provide city of the apartment")
        String city,

        @NotBlank(message = "Provide state of the apartment")
        String state,

        @NotBlank(message = "Provide country of the apartment")
        String country,

        @JsonProperty("zip_code")
        @NotBlank(message = "Provide zip code of the apartment")
        String zipCode
) {
}
