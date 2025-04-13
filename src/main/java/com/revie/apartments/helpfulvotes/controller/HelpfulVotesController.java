package com.revie.apartments.helpfulvotes.controller;

import com.revie.apartments.helpfulvotes.service.HelpfulVotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Helpful Votes", description = "API for managing votes on reviews")
public class HelpfulVotesController {
    private final HelpfulVotesService helpfulVotesService;

    @PostMapping("/{reviewId}")
    @Operation(summary = "Vote on a review")
    public ResponseEntity<?> voteOnReview(
            @PathVariable String reviewId
            ) {
        return ResponseEntity.ok(helpfulVotesService.toggleHelpfulVotes(reviewId));
    }
}
