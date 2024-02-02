package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final ReviewRepository reviewRepository;
    @Override
    public Page<ReviewDTO> getReviewPageList(Pageable pageable, int pageNo, String criteria, ProductDTO productDTO){
        pageable = PageRequest.of(pageNo, 5, Sort.by(Sort.Direction.DESC, criteria));
        Page<Review> reviewPageList = reviewRepository.findAllByProduct(Product.toProductEntity(productDTO), pageable);
        Page<ReviewDTO> reviewDTOPageList = reviewPageList.map(ReviewDTO::toReviewDTO);
        return reviewDTOPageList;
    }
}
