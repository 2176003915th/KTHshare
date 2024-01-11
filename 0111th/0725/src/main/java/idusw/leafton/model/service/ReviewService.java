package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> viewAllReviews(Long productId);
    ReviewDTO insertReview(Long productId, String reviewContent);
}
