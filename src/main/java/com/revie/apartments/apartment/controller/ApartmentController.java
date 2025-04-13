package com.revie.apartments.apartment.controller;

import com.revie.apartments.apartment.dto.request.ApartmentRequestDto;
import com.revie.apartments.apartment.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apartment")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Apartment", description = "APIs for managing apartment listings")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @PostMapping("new")
    @Operation(summary = "Add new apartment")
    public ResponseEntity<?> newApartment(
            @Valid
            @RequestBody
            ApartmentRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                apartmentService.createApartment(requestDto));
    }

    @GetMapping("/{apartmentId}")
    @Operation(summary = "Get apartment by ID")
    public ResponseEntity<?> getApartmentById(
            @PathVariable String apartmentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                apartmentService.getApartmentById(apartmentId));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all apartments")
    public ResponseEntity<?> getAllApartments() {
        return ResponseEntity.status(HttpStatus.OK).body(
                apartmentService.getAllApartments());
    }
}
