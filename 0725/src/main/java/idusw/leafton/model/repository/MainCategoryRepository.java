package idusw.leafton.model.repository;

import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainCategoryRepository  extends JpaRepository<MainCategory, Long> {
    List<MainCategory> findAll();
    Optional<MainCategory> findById(Long MainCategoryId);
    @Override
    <S extends MainCategory> S save(S mainCategory);
}
