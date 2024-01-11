package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.repository.ProductRepository;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    @Override
    public List<ReviewDTO> viewAllReviews(Long productId) {
        ProductDTO productid = productService.getProductDTOId(productId); // 객체지향 컬럼 getProductDTOId 메소드에서 Long-> ProductDTO 변환
        List<Review> reviewList = reviewRepository.findAllByProduct(Product.toProductEntity(productid));
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(Review review: reviewList) {
            reviewDTOList.add(ReviewDTO.toReviewDTO(review));  //dto리스트에 reviwe entity 리스트 추가
        }
        return reviewDTOList;
    }

    @Override
    public ReviewDTO insertReview(Long productId, String reviewContent) {
        ProductDTO productid = productService.getProductDTOId(productId);
        Review review = new Review(); //받은 DTO에서 entity로 변환
        review.setProduct(Product.toProductEntity(productid)); //dto메서드 바로사용
        review.setContent(reviewContent);
        review.setRegistDate(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review); //변환된 결과값 저장

        ReviewDTO savedReviewDTO = new ReviewDTO(); // 다시 변환된 결과를 DTO로 변환함
        savedReviewDTO.setProductDTO(savedReviewDTO.getProductDTO());
        savedReviewDTO.setContent(savedReview.getContent());
        savedReviewDTO.setRegistDate(LocalDateTime.now());
        return savedReviewDTO;
    }


}
