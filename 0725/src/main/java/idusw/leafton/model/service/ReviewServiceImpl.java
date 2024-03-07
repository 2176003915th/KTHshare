package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDTO viewDetailReview(MemberDTO memberDTO, ProductDTO productDTO){
        Optional<Review> opReview = reviewRepository.findByMemberAndProduct(Member.toMemberEntity(memberDTO), Product.toProductEntity(productDTO));
        ReviewDTO reviewDTO = new ReviewDTO();
        if (opReview.isPresent()) {
            Review review = opReview.get();
            reviewDTO = ReviewDTO.toReviewDTO(review);
            // findById로 받아온 결과값들을 DTO에 저장함

            return reviewDTO;
        } else {
            return null;
        }
    };

    @Override
    public ReviewDTO insertReview(ReviewDTO reviewDTO) {
        Review review = Review.toReviewEntity(reviewDTO); // entity에 넣기위하여 변경
        Review result = reviewRepository.save(review); // 레파지토리에서 save(insert)한 결과
        return ReviewDTO.toReviewDTO(result); //결과를 dto에 저장
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
    @Override
    public Double getAvgRating(Long productId) {
        Optional<Double> opAvgRating = reviewRepository.getAverageRatingByProduct(productId);
        if(opAvgRating.isPresent()) {
            return opAvgRating.get();
        } else {
            return null;
        }
    }


}
