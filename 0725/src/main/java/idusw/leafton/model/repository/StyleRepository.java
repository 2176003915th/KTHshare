package idusw.leafton.model.repository;

import idusw.leafton.model.entity.Style;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    @Override
    Optional<Style> findById(Long styleId);
}