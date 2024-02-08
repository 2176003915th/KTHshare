package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<ReviewDTO> getReviewPageList(Pageable reviewPageable, int pageNo, String criteria, ProductDTO productDTO);
}
