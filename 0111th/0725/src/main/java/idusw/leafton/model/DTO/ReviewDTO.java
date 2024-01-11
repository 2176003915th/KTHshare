package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private ProductDTO productDTO;
    private MemberDTO memberDTO;
    private int rating;
    private String content;
    private LocalDateTime registDate;

    public static ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setProductDTO(ProductDTO.toProductDTO(review.getProduct())); //참조부분 직접 바로 메서드 사용하여 호출
        reviewDTO.setRating(review.getRating());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setRegistDate(review.getRegistDate());

        return reviewDTO;
    }
}