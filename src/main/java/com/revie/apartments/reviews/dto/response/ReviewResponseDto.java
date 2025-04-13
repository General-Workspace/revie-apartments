package com.revie.apartments.reviews.dto.response;

import com.revie.apartments.media.dto.response.MediaResponse;
import com.revie.apartments.reviews.dto.ApartmentResponse;
import com.revie.apartments.reviews.dto.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReviewResponseDto(
        String id,
        UserResponse user,
        ApartmentResponse apartment,
        Integer landlordRating,
        Integer environmentRating,
        Integer amenitiesRating,
        String comment,
        long helpfulVotes,
        LocalDateTime createdAt,
        List<MediaResponse> media
) {}
