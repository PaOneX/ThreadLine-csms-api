package edu.icet.repository;

import edu.icet.model.dto.CategoryDto;
import edu.icet.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category entity);

    boolean existsById(Long id);

    void deleteById(Long id);
}
