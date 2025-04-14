package com.revie.apartments.reviews.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Builder
@Schema(description = "Review data")
public record ReviewRequestDto(
        @Schema(description = "Apartment ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6afd4959-dc43-4e09-a7e8-aa1417af8ab3")
        @NotNull
        String apartmentId,

        @Schema(description = "Rating for landlord (1-5)", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
        @Min(1) @Max(5)
        Integer landlordRating,

        @Schema(description = "Rating for environment (1-5)", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
        @Min(1) @Max(5)
        Integer environmentRating,

        @Schema(description = "Rating for amenities (1-5)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
        @Min(1) @Max(5)
        Integer amenitiesRating,

        @Schema(description = "Review comment", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Great place to live!")
        String comment,

        @Schema(description = "Media files for the review", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
                List<MultipartFile> mediaFiles
) {}
