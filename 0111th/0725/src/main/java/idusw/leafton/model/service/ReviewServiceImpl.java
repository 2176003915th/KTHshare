package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.repository.ProductRepository;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    @Override
    public List<ReviewDTO> viewAllReviews(ProductDTO productid) {
        List<Review> reviewList = reviewRepository.findAllByProduct(Product.toProductEntity(productid)); //엔티티형인 ProductDTO가아닌 Product형으로 바꺼야하기때문에 패러미터수정
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(Review review: reviewList) {
            reviewDTOList.add(ReviewDTO.toReviewDTO(review));  //dto리스트에 reviwe entity 리스트 추가
        }
        return reviewDTOList;
    }

    @Override
    public ReviewDTO insertReview(ProductDTO productid, MemberDTO memberid, String reviewContent, Integer rating) {
        Review review = new Review(); //받은 DTO에서 entity로 변환
        review.setProduct(Product.toProductEntity(productid)); //dto메서드 바로사용
        review.setMemberId(Member.toMemberEntity(memberid));
        review.setContent(reviewContent);
        review.setRating(rating);
        review.setRegistDate(LocalDate.now());
        Review savedReview = reviewRepository.save(review); //변환된 결과값 저장

        ReviewDTO savedReviewDTO = new ReviewDTO(); // 다시 변환된 결과를 DTO로 변환함
        savedReviewDTO.setProductDTO(savedReviewDTO.getProductDTO());
        savedReviewDTO.setMemberDTO(savedReviewDTO.getMemberDTO());
        savedReviewDTO.setContent(savedReview.getContent());
        savedReviewDTO.setRating(savedReview.getRating());
        savedReviewDTO.setRegistDate(LocalDate.now());
        return savedReviewDTO;
    }
    @Override
    public double getAvgRating(Long productId) {
        return reviewRepository.getAverageRating(productId);
    }

}
