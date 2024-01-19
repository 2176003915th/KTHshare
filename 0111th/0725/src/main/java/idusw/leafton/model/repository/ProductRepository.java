package idusw.leafton.model.repository;

import com.sun.tools.javac.Main;
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
}