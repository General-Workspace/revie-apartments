package com.revie.apartments.apartment.repository;

import com.revie.apartments.apartment.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, String> {
}
