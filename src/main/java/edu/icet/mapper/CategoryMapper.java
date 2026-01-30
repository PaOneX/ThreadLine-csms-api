package edu.icet.mapper;

import edu.icet.model.dto.CategoryDto;
import edu.icet.model.dto.CategoryRequestDto;
import edu.icet.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);

    CategoryDto toDto(Category entity);

    List<CategoryDto> toDtoList(List<Category> entities);

    void updateEntityFromDto(CategoryDto dto, @MappingTarget Category entity);

    CategoryDto toDto(CategoryRequestDto requestDto);

    Category toEntity(CategoryRequestDto dto);

    void updateEntityFromDto(CategoryRequestDto dto, @MappingTarget Category entity);
}
