package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CategoryRefDto", description = "Reference to an existing category (only id)")
public class CategoryRefDto {
    @Schema(description = "Category identifier", example = "1")
    private Long id;
}
