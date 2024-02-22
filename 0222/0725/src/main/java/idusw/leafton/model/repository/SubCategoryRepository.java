package idusw.leafton.model.repository;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository  extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByMainCategory(MainCategory mainCategory);
    Optional<SubCategory> findById(Long subCategoryId);
    <S extends SubCategory> S save(S subCategory);
}
