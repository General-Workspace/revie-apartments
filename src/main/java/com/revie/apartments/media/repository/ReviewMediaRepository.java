package com.revie.apartments.media.repository;

import com.revie.apartments.media.entity.ReviewMedia;
import com.revie.apartments.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewMediaRepository extends JpaRepository<ReviewMedia, String> {
    List<ReviewMedia> findByReview(Review review);
}
