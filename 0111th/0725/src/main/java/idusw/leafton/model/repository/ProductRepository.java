package idusw.leafton.model.repository;

import com.sun.tools.javac.Main;
import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.SubCategory;
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
    List<Product> findAllByMainCategory(MainCategory mainCategory);
    List<Product> findAllByMainCategoryAndSubCategory(MainCategory mainCategory,SubCategory subCategory);
    List<Product> findAllByMainMaterial(MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterial(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory mainCategory, SubCategory subCategory,MainMaterial mainMaterial);

    //정렬 부분 시작
    List<Product> findAllByOrderByNameAsc();
    List<Product> findAllByOrderByRegistDateAsc();
    List<Product> findAllByOrderByRatingDesc();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByMainCategoryOrderByNameAsc(MainCategory mainCategory);
    List<Product> findAllByMainCategoryOrderByRegistDateAsc(MainCategory mainCategory);
    List<Product> findAllByMainCategoryOrderByRatingDesc(MainCategory mainCategory);
    List<Product> findAllByMainCategoryOrderByPriceAsc(MainCategory mainCategory);
    List<Product> findAllByMainCategoryOrderByPriceDesc(MainCategory mainCategory);
    List<Product> findAllByMainCategoryAndSubCategoryOrderByNameAsc(MainCategory mainCategory, SubCategory subCategory);
    List<Product> findAllByMainCategoryAndSubCategoryOrderByRegistDateAsc(MainCategory mainCategory, SubCategory subCategory);
    List<Product> findAllByMainCategoryAndSubCategoryOrderByRatingDesc(MainCategory mainCategory, SubCategory subCategory);
    List<Product> findAllByMainCategoryAndSubCategoryOrderByPriceAsc(MainCategory mainCategory, SubCategory subCategory);
    List<Product> findAllByMainCategoryAndSubCategoryOrderByPriceDesc(MainCategory mainCategory, SubCategory subCategory);
    List<Product> findAllByMainMaterialOrderByNameAsc(MainMaterial mainMaterial);
    List<Product> findAllByMainMaterialOrderByRegistDateAsc(MainMaterial mainMaterial);
    List<Product> findAllByMainMaterialOrderByRatingDesc(MainMaterial mainMaterial);
    List<Product> findAllByMainMaterialOrderByPriceAsc(MainMaterial mainMaterial);
    List<Product> findAllByMainMaterialOrderByPriceDesc(MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterialOrderByNameAsc(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterialOrderByRegistDateAsc(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterialOrderByRatingDesc(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterialOrderByPriceAsc(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndMainMaterialOrderByPriceDesc(MainCategory mainCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByNameAsc(MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByRegistDateAsc(MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByRatingDesc(MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByPriceAsc(MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    List<Product> findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByPriceDesc(MainCategory mainCategory, SubCategory subCategory, MainMaterial mainMaterial);
    // 정렬 부분 끝
}
