package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SupplierRequestDto", description = "Supplier create/update request")
public class SupplierRequestDto {
    @NotBlank(message = "name must not be blank")
    @Schema(description = "Supplier name", example = "ABC Textiles", required = true)
    private String name;

    @Email(message = "email must be valid")
    @Schema(description = "Supplier email", example = "contact@abctextiles.com")
    private String email;

    @Schema(description = "Supplier phone", example = "+1-234-567-8900")
    private String phone;

    @Schema(description = "Supplier address", example = "123 Main St, City")
    private String address;
}
