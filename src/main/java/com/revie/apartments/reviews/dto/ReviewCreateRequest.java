package com.revie.apartments.reviews.dto;

import com.revie.apartments.reviews.dto.request.ReviewRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "Request DTO for creating a review")
public record ReviewCreateRequest(
        @Schema(description = "Review data", requiredMode = Schema.RequiredMode.REQUIRED)
        @Valid
        ReviewRequestDto request,

        @Schema(description = "List of media files", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @RequestPart(value = "mediaFiles", required = false)
        List<MultipartFile> mediaFiles
) {}
