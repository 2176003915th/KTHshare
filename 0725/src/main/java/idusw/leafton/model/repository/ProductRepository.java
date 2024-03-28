package idusw.leafton.model.repository;

import com.sun.tools.javac.Main;
import idusw.leafton.model.DTO.EventDTO;
import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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


    Optional<Product> findById(Long productId);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByMainCategory(Pageable pageable, MainCategory mainCategory);
    Page<Product> findAllByMainCategoryAndSubCategory(Pageable pageable, MainCategory mainCategory,SubCategory subCategory);
    Page<Product> findAllByMainMaterial(Pageable pageable,MainMaterial mainMaterial);
    Page<Product> findAllByMainCategoryAndMainMaterial(Pageable pageable,MainCategory mainCategory, MainMaterial mainMaterial);
    Page<Product> findAllByMainCategoryAndSubCategoryAndMainMaterial(Pageable pageable,MainCategory mainCategory, SubCategory subCategory,MainMaterial mainMaterial);
    Page<Product> findAllByEvent(Pageable pageable,Event event);
    Page<Product> findAllByEventAndMainCategory(Pageable pageable,Event event, MainCategory mainCategory);
    Page<Product> findAllByEventAndMainCategoryAndSubCategory(Pageable pageable,Event event, MainCategory mainCategory, SubCategory subCategory);
    Page<Product> findAllByEventAndMainMaterial(Pageable pageable,Event event, MainMaterial mainMaterial);
    Page<Product> findAllByEventAndMainCategoryAndMainMaterial(Pageable pageable,Event event, MainCategory mainCategory,MainMaterial mainMaterial);
    Page<Product> findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Pageable pageable,Event event, MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    Page<Product> findAllByNameContaining(Pageable pageable,String name);
    Page<Product> findAllByMainCategoryNameContaining(Pageable pageable,String name);
    Page<Product> findAllBySubCategoryNameContaining(Pageable pageable,String name);
    Page<Product> findAllByMainMaterialNameContaining(Pageable pageable,String name);

    @Query("select p from Product p where p.mainCategory.mainCategoryId = :mainCategoryId order by RAND() limit 8")
    List<Product> findRandomProductsByMainCategory(@Param("mainCategoryId") Long mainCategoryId);

    @Query("select p from Product p order by RAND() limit 8")
    List<Product> findRandomProducts();

    @Query("select p from Product p where p.salePercentage > 0 order by RAND() limit 8")
    List<Product> findRandomProductsBySalePercentage();

}
