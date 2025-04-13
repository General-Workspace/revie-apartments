package com.revie.apartments.reviews.repository;

import com.revie.apartments.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByApartmentIdOrderByCreatedAtDesc(String apartmentId);

    @Query("SELECT r FROM Review r WHERE r.apartment.id = :apartmentId ORDER BY " +
            "(SELECT COUNT(hv) FROM HelpfulVotes hv WHERE hv.review = r) DESC")
    List<Review> findByApartmentIdOrderByHelpfulVotesDesc(String apartmentId);
}
