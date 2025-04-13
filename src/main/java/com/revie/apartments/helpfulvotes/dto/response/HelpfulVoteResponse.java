package com.revie.apartments.helpfulvotes.dto.response;

import lombok.Builder;

@Builder
public record HelpfulVoteResponse(
        String reviewId,
        long totalVotes
) {
}
