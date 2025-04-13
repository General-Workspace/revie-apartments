package com.revie.apartments.apartment.service;

import com.revie.apartments.apartment.dto.request.ApartmentRequestDto;
import com.revie.apartments.apartment.dto.response.ApartmentResponseDto;
import com.revie.apartments.apartment.entity.Apartment;
import com.revie.apartments.apartment.repository.ApartmentRepository;
import com.revie.apartments.apartment.dto.ApartmentDataDto;
import com.revie.apartments.exceptions.ForbiddenException;
import com.revie.apartments.exceptions.NotFoundException;
import com.revie.apartments.exceptions.UnauthorizedException;
import com.revie.apartments.user.entity.User;
import com.revie.apartments.user.enums.UserRole;
import com.revie.apartments.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApartmentResponseDto createApartment(ApartmentRequestDto apartmentRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("You must be logged in."));

        if (user.getUserRole() != UserRole.LANDLORD) {
            throw new ForbiddenException("Only landlords can create an apartment.");
        }

        Apartment apartment = new Apartment();
        apartment.setAddress(apartmentRequestDto.address());
        apartment.setCity(apartmentRequestDto.city());
        apartment.setCountry(apartmentRequestDto.country());
        apartment.setState(apartmentRequestDto.state());
        apartment.setZipCode(apartmentRequestDto.zipCode());
        apartment.setCreatedBy(user);

        apartmentRepository.save(apartment);

        return ApartmentResponseDto.builder()
                .status_code(HttpStatus.CREATED)
                .status("success")
                .message("Apartment created successfully")
                .data(mapToApartmentDataDto(apartment))
                .build();
    }

    public ApartmentResponseDto getApartmentById(String apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new NotFoundException("Apartment not found"));

        return mapToApartmentResponseDto(apartment);
    }

    public List<ApartmentResponseDto> getAllApartments() {
        List<Apartment> apartments = apartmentRepository.findAll();

        return apartments.stream()
                .map(this::mapToApartmentResponseDto)
                .toList();
    }

    private ApartmentDataDto mapToApartmentDataDto(Apartment apartment) {
        return ApartmentDataDto.builder()
                .id(apartment.getId())
                .address(apartment.getAddress())
                .city(apartment.getCity())
                .state(apartment.getState())
                .country(apartment.getCountry())
                .zip_code(apartment.getZipCode())
                .user_id(apartment.getCreatedBy().getId())
                .created_at(apartment.getCreatedAt())
                .updated_at(apartment.getUpdatedAt())
                .build();
    }

    private ApartmentResponseDto mapToApartmentResponseDto(Apartment apartment) {
        return ApartmentResponseDto.builder()
                .status_code(HttpStatus.OK)
                .status("success")
                .message("Apartment retrieved successfully")
                .data(mapToApartmentDataDto(apartment))
                .build();
    }
}
