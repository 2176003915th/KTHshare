package idusw.leafton.model.repository;

import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findAllByProduct(Product product);

}
