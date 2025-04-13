package com.revie.apartments.helpfulvotes.entity;

import com.revie.apartments.reviews.entity.Review;
import com.revie.apartments.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class HelpfulVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @JoinColumn(name = "voter_session_id", nullable = false)
    private String voterSessionId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;
}
