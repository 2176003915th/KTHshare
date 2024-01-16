package idusw.leafton.model.repository;

import com.sun.tools.javac.Main;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findAll();

    Optional<Product> findById(Long productId);
    List<Product> findAllByMainCategory(MainCategory mainCategory);
}
