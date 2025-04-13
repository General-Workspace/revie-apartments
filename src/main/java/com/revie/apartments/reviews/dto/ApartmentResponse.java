package com.revie.apartments.reviews.dto;

import lombok.Builder;

@Builder
public record ApartmentResponse(
        String id,
        String address,
        String city,
        String state,
        String zip_code,
        String country,
        String user_id
) {
}
