package edu.icet.model.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "SupplierDto", description = "Supplier data transfer object")
public class SupplierDto {
//    @Schema(description = "Supplier identifier", example = "1")
    private Long id;

//    @Schema(description = "Supplier name", example = "ABC Textiles")
    private String name;

//    @Schema(description = "Supplier email", example = "contact@abctextiles.com")
    private String email;

//    @Schema(description = "Supplier phone", example = "+1-234-567-8900")
    private String phone;

//    @Schema(description = "Supplier address", example = "123 Main St, City")
    private String address;
}
