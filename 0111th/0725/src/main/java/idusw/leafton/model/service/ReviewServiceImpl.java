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
    public ReviewDTO insertReview(ReviewDTO reviewDTO) {
        Review review = Review.toReviewEntity(reviewDTO); // entity에 넣기위하여 변경
        Review result = reviewRepository.save(review); // 레파지토리에서 save(insert)한 결과
        return ReviewDTO.toReviewDTO(result); //결과를 dto에 저장
    }
    @Override
    public double getAvgRating(Long productId) {
        return reviewRepository.getAverageRating(productId);
    }

}
