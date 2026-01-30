package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CategoryDto", description = "Category data transfer object")
public class CategoryDto {
    @Schema(description = "Category identifier", example = "1")
    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(max = 255, message = "name must be at most 255 characters")
    @Schema(description = "Category name", example = "Shirts", required = true)
    private String name;
}
