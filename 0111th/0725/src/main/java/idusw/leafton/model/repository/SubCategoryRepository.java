package idusw.leafton.model.repository;

import idusw.leafton.model.entity.Review;
import idusw.leafton.model.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository  extends JpaRepository<SubCategory, Long> {
    @Query("SELECT s FROM SubCategory s where s.mainCategory.mainCategoryId = :Id")
    List<SubCategory> findByMainCategoryId(@Param("Id") Long mainCategoryId);
}
