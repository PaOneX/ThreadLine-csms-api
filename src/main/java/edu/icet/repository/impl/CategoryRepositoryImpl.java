package edu.icet.repository.impl;

import edu.icet.model.entity.Category;
import edu.icet.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Category save(Category entity) {
        entity.onCreate();
        String sql = "INSERT INTO `category` (`name`,`description`) VALUES (?,?)";
        return jdbcTemplate.update(sql,entity.getName(), entity.getDescription())>0 ? entity : null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }
}
