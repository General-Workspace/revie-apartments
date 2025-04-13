package com.revie.apartments.helpfulvotes.service;

import com.revie.apartments.exceptions.NotFoundException;
import com.revie.apartments.helpfulvotes.dto.response.HelpfulVoteResponse;
import com.revie.apartments.helpfulvotes.entity.HelpfulVotes;
import com.revie.apartments.helpfulvotes.repository.HelpfulVotesRepository;
import com.revie.apartments.reviews.entity.Review;
import com.revie.apartments.reviews.repository.ReviewRepository;
import com.revie.apartments.user.entity.User;
import com.revie.apartments.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HelpfulVotesService {
    private final HelpfulVotesRepository helpfulVotesRepository;
    private final ReviewRepository reviewRepository;
    private final HttpServletRequest request;

    @Transactional
    public HelpfulVoteResponse toggleHelpfulVotes(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found"));

        String sessionId = getVoterSessionId();

        Optional<HelpfulVotes> existingVote = helpfulVotesRepository.findByReviewAndVoterSessionId(review, sessionId);

        if (existingVote.isPresent()) {
            helpfulVotesRepository.delete(existingVote.get());
        } else {
            HelpfulVotes helpfulVote = new HelpfulVotes();
            helpfulVote.setReview(review);
            helpfulVote.setVoterSessionId(sessionId);
            helpfulVotesRepository.save(helpfulVote);
        }

        long totalVotes = helpfulVotesRepository.countByReview(review);
        return HelpfulVoteResponse.builder()
                .reviewId(reviewId)
                .totalVotes(totalVotes)
                .build();
    }

    private String getVoterSessionId() {
        // Use a combination of IP and User-Agent to identify unique visitors
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String sessionKey = ipAddress + "_" + (userAgent != null ? userAgent : "");

        // Hash the combination for privacy
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }
}
