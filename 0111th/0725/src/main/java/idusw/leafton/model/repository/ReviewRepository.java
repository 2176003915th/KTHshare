package idusw.leafton.model.repository;

import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findAllByProduct(Product product); //엔티티이기에 PrudctDTO productid 는 되지않음

    @Query("SELECT AVG(r.rating) FROM Review r where r.product.productId = :Id") //리뷰테이블 product필드안 (product테이블) productId
    Double getAverageRating(@Param("Id") Long productId); //쿼리문의 productId와 매개변수를 맞추기위해 @Param 사용
    //해당 jpa쿼리문에서 review필드값에 productDTO객체를 직접사용하는것을 불가함. ex Long형에서 productDTO형으로 바꾼 productid를 직접 쿼리문에서 사용하는것을 불가
    // 그래서 controller에서 패러미터받아올때 productDTO형으로 변경해줄필요가없음. Long형으로 바로대입.

    @Override
    <S extends Review> S save(S reivew);
}
