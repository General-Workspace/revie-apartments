package com.revie.apartments.reviews.controller;

import com.revie.apartments.reviews.dto.request.ReviewRequestDto;
import com.revie.apartments.reviews.dto.response.ReviewResponseDto;
import com.revie.apartments.reviews.entity.Review;
import com.revie.apartments.reviews.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
            @RequestPart("request") @Valid ReviewRequestDto request,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
            ) {

        request.setMediaFiles(mediaFiles);

        if (mediaFiles != null) {
            for (MultipartFile file : mediaFiles) {
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
