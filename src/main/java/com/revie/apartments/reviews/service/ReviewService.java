package com.revie.apartments.reviews.service;

import com.revie.apartments.apartment.dto.ApartmentDataDto;
import com.revie.apartments.apartment.entity.Apartment;
import com.revie.apartments.apartment.repository.ApartmentRepository;
import com.revie.apartments.exceptions.NotFoundException;
import com.revie.apartments.helpfulvotes.repository.HelpfulVotesRepository;
import com.revie.apartments.media.dto.response.MediaResponse;
import com.revie.apartments.media.entity.ReviewMedia;
import com.revie.apartments.media.repository.ReviewMediaRepository;
import com.revie.apartments.media.service.MediaStorageService;
import com.revie.apartments.reviews.dto.ApartmentResponse;
import com.revie.apartments.reviews.dto.UserResponse;
import com.revie.apartments.reviews.dto.request.ReviewRequestDto;
import com.revie.apartments.reviews.dto.response.ReviewResponseDto;
import com.revie.apartments.reviews.entity.Review;
import com.revie.apartments.reviews.repository.ReviewRepository;
import com.revie.apartments.user.entity.User;
import com.revie.apartments.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final ReviewMediaRepository mediaRepository;
    private final HelpfulVotesRepository helpfulVotesRepository;
    private final MediaStorageService mediaStorageService;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User does not exist."));

        Apartment apartment = apartmentRepository.findById(request.apartmentId())
                .orElseThrow(() -> new NotFoundException("Apartment does not exist."));

        // Create a new Review entity
        Review review = new Review();
        review.setApartment(apartment);
        review.setUser(user);
        review.setLandlordRating(request.landlordRating());
        review.setEnvironmentRating(request.environmentRating());
        review.setAmenitiesRating(request.amenitiesRating());
        review.setComment(request.comment());

        // Save the review
        Review savedReview = reviewRepository.save(review);

        // Handle media files if any
        if (!request.getMediaFiles().isEmpty()) {
            request.getMediaFiles().stream()
                    .filter(file -> !file.isEmpty())
                    .forEach(file -> {
                        try {
                            String filename = mediaStorageService.saveFile(file);
                            String mediaType = Objects.requireNonNull(file.getContentType()).startsWith("image/") ? "image" : "video";
                            ReviewMedia media = new ReviewMedia();
                            media.setReview(savedReview);
                            media.setMediaType(mediaType);
                            media.setFilePath(filename);
                            media.setFileName(file.getOriginalFilename());
                            mediaRepository.save(media);
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    });
        }

        // Return the saved review as a response DTO
        return mapReviewToResponse(savedReview);
    }

    public List<ReviewResponseDto> getReviewsByApartmentId(String apartmentId, String sortBy) {
        List<Review> reviews;

        if ("helpful".equalsIgnoreCase(sortBy)) {
            reviews = reviewRepository.findByApartmentIdOrderByHelpfulVotesDesc(apartmentId);
        } else {
            reviews = reviewRepository.findByApartmentIdOrderByCreatedAtDesc(apartmentId);
        }

        if (reviews.isEmpty()) {
            throw new NotFoundException("No reviews found for this apartment.");
        }

        return reviews.stream()
                .map(this::mapReviewToResponse)
                .toList();
    }

    public ReviewResponseDto getReviewById(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found."));

        return mapReviewToResponse(review);
    }

    private UserResponse mapUserToDataResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .user_role(user.getUserRole())
                .build();
    }

    private ApartmentResponse mapApartmentToDataResponse(Apartment apartment) {
        return ApartmentResponse.builder()
                .id(apartment.getId())
                .address(apartment.getAddress())
                .city(apartment.getCity())
                .state(apartment.getState())
                .country(apartment.getCountry())
                .zip_code(apartment.getZipCode())
                .user_id(apartment.getCreatedBy().getId())
                .build();
    }

    private ReviewResponseDto mapReviewToResponse(Review review) {
        long helpfulVotesCount = helpfulVotesRepository.countByReview(review);

        List<ReviewMedia> mediaFiles = mediaRepository.findByReview(review);
        List<MediaResponse> mediaResponses = mediaFiles.stream()
                .map(media -> MediaResponse.builder()
                        .id(media.getId())
                        .mediaType(media.getMediaType())
                        .mediaUrl(mediaStorageService.getMediaUrl(media.getFilePath()))
                        .build())
                .toList();

        return ReviewResponseDto.builder()
                .id(review.getId())
                .user(mapUserToDataResponse(review.getUser()))
                .apartment(mapApartmentToDataResponse(review.getApartment()))
                .landlordRating(review.getLandlordRating())
                .environmentRating(review.getEnvironmentRating())
                .amenitiesRating(review.getAmenitiesRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .helpfulVotes(helpfulVotesCount)
                .media(mediaResponses)
                .build();
    }
}
