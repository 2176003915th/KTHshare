package idusw.leafton.model.repository;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainMaterialRepository extends JpaRepository<MainMaterial, Long>{
    List<MainMaterial> findAll();
    Optional<MainMaterial> findById(Long MainMaterialId);
}
