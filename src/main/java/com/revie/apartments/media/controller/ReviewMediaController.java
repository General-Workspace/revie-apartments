package com.revie.apartments.media.controller;

import com.revie.apartments.exceptions.NotFoundException;
import com.revie.apartments.media.entity.ReviewMedia;
import com.revie.apartments.media.repository.ReviewMediaRepository;
import com.revie.apartments.media.service.MediaStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Review Media", description = "APIs for accessing review media")
public class ReviewMediaController {
    private final ReviewMediaRepository reviewMediaRepository;
    private final MediaStorageService mediaStorageService;

    @GetMapping("/{mediaId}")
    @Operation(summary = "Get review media by ID")
    public ResponseEntity<?> getReviewMedia(@PathVariable String mediaId) {
        ReviewMedia media = reviewMediaRepository.findById(mediaId)
                .orElseThrow(() -> new NotFoundException("Media not found"));

        // Load the media file from storage
        UrlResource resource = mediaStorageService.loadFile(media.getFilePath());

        String contentType = String.valueOf("image".equals(media.getMediaType()) ? MediaType.IMAGE_JPEG : MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + media.getFileName() + "\"")
                .body(resource);
    }
}
