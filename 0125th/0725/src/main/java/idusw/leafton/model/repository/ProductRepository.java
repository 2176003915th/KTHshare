package idusw.leafton.model.repository;

import com.sun.tools.javac.Main;
import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.entity.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findAll();
    Optional<Product> findById(Long productId);
    List<Product> findAllByMainCategory(MainCategory mainCategory, Sort name);
    List<Product> findAllByMainCategoryAndSubCategory(MainCategory mainCategory,SubCategory subCategory, Sort name);
    List<Product> findAllByMainMaterial(MainMaterial mainMaterial, Sort name);
    List<Product> findAllByMainCategoryAndMainMaterial(MainCategory mainCategory, MainMaterial mainMaterial, Sort name);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory mainCategory, SubCategory subCategory,MainMaterial mainMaterial, Sort name);
    List<Product> findAllByEvent(Event event, Sort name);
    List<Product> findAllByEventAndMainCategory(Event event, MainCategory mainCategory, Sort name);
    List<Product> findAllByEventAndMainCategoryAndSubCategory(Event event, MainCategory mainCategory, SubCategory subCategory ,Sort name);
    List<Product> findAllByEventAndMainMaterial(Event event, MainMaterial mainMaterial, Sort name);
    List<Product> findAllByEventAndMainCategoryAndMainMaterial(Event event, MainCategory mainCategory,MainMaterial mainMaterial, Sort name);
    List<Product> findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event event, MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial,Sort name);
}
