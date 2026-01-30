package edu.icet.service.impl;

import edu.icet.exception.CategoryNotFoundException;
import edu.icet.mapper.CategoryMapper;
import edu.icet.model.dto.CategoryDto;
import edu.icet.model.entity.Category;
import edu.icet.repository.CategoryRepository;
import edu.icet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> list = categoryRepository.findAll();
        return categoryMapper.toDtoList(list);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category entity = categoryMapper.toEntity(categoryDto);
        Category saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existing = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryMapper.updateEntityFromDto(categoryDto, existing);
        Category saved = categoryRepository.save(existing);
        return categoryMapper.toDto(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }
}
