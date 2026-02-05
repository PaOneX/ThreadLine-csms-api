package edu.icet.model.dto;

import edu.icet.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InvoiceRequestDto", description = "Invoice create/update request")
public class InvoiceRequestDto {
    @NotNull(message = "orderId must not be null")
    @Schema(description = "Order ID", example = "1", required = true)
    private Long orderId;

    @Schema(description = "Invoice number", example = "INV-2024-001")
    private String invoiceNumber;

    @Schema(description = "Tax amount", example = "15.99")
    private BigDecimal taxAmount;

    @Schema(description = "Net amount", example = "199.99")
    private BigDecimal netAmount;

    @Schema(description = "Invoice status")
    private Status status;
}
