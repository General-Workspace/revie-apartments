package com.revie.apartments.reviews.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Builder
public record ReviewRequestDto(
        @NotNull
        @JsonProperty("apartment_id")
        String apartmentId,

        @JsonProperty("landlord_rating")
        @Min(1) @Max(5)
        Integer landlordRating,

        @JsonProperty("environment_rating")
        @Min(1) @Max(5)
        Integer environmentRating,

        @JsonProperty("amenities_rating")
        @Min(1) @Max(5)
        Integer amenitiesRating,

        String comment
) {
        @JsonIgnore
        private static List<MultipartFile> mediaFiles;

        public List<MultipartFile> getMediaFiles() {
                return mediaFiles != null ? mediaFiles : Collections.emptyList();
        }

        public void setMediaFiles(List<MultipartFile> mediaFiles) {
                ReviewRequestDto.mediaFiles = mediaFiles;
        }

}
