package com.a103.apiServer.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Review;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {
	List<Review> findListReviewByProductId(long productId);
	List<Review> findListReviewByMemberId(long memberId);
}
