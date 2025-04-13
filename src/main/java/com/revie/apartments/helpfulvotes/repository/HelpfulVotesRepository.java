package com.revie.apartments.helpfulvotes.repository;

import com.revie.apartments.helpfulvotes.entity.HelpfulVotes;
import com.revie.apartments.reviews.entity.Review;
import com.revie.apartments.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HelpfulVotesRepository extends JpaRepository<HelpfulVotes, String> {
    Optional<HelpfulVotes> findByReviewAndVoterSessionId(Review review, String voterSessionId);
    long countByReview(Review review);
}
