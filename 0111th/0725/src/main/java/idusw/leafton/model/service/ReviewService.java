package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> viewAllReviews(ProductDTO productDTO);
    ReviewDTO insertReview(ReviewDTO reviewDTO);
    Double getAvgRating(Long productId);
}
