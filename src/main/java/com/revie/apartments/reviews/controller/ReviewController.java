package com.revie.apartments.reviews.controller;

import com.revie.apartments.reviews.dto.request.ReviewRequestDto;
import com.revie.apartments.reviews.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Reviews", description = "API for managing apartment reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new review")
    public ResponseEntity<?> createReview(
            @RequestParam @Valid String apartmentId,
            @RequestParam @Valid @Min(1) @Max(5) Integer landlordRating,
            @RequestParam @Valid @Min(1) @Max(5) Integer environmentRating,
            @RequestParam @Valid @Min(1) @Max(5) Integer amenitiesRating,
            @RequestParam(required = false) String comment,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
//            @Valid @ModelAttribute ReviewRequestDto request
    ) {

        ReviewRequestDto request = ReviewRequestDto.builder()
                .apartmentId(apartmentId)
                .landlordRating(landlordRating)
                .environmentRating(environmentRating)
                .amenitiesRating(amenitiesRating)
                .comment(comment)
                .mediaFiles(mediaFiles != null ? mediaFiles : List.of())
                .build();

        if (request.mediaFiles() != null) {
            for (MultipartFile file : request.mediaFiles()) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    throw new IllegalArgumentException("File size exceeds the limit of 10MB");
                }
            }
        }

        return ResponseEntity.ok(reviewService.createReview(request));
    }

    @GetMapping("/apartment/{apartmentId}")
    @Operation(summary = "Get reviews for an apartment")
    public ResponseEntity<?> getReviewsByApartmentId(
            @PathVariable String apartmentId,
            @RequestParam(required = false) String sortBy
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByApartmentId(apartmentId, sortBy));
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get a review by ID")
    public ResponseEntity<?> getReviewById(@PathVariable String reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }
}