package com.revie.apartments.media.dto.response;

import lombok.Builder;

@Builder
public record MediaResponse(
        String id,
        String mediaType,
        String mediaUrl
) {
}
