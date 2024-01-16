package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> viewAllReviews(ProductDTO productid);
    ReviewDTO insertReview(ProductDTO productid, MemberDTO memberid, String reviewContent , Integer rating);
    double getAvgRating(Long productId);
}
