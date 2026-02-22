package edu.icet.model.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "InventoryRequestDto", description = "Inventory create/update request")
public class InventoryRequestDto {
//    @NotNull(message = "variantId must not be null")
//    @Schema(description = "Product variant ID", example = "1", required = true)
    private Long variantId;

//    @NotNull(message = "quantity must not be null")
//    @Min(value = 0, message = "quantity must be at least 0")
//    @Schema(description = "Available quantity", example = "100", required = true)
    private Integer quantity;
}
