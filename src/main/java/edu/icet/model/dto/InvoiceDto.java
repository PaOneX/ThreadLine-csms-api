package edu.icet.model.dto;

import edu.icet.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InvoiceDto", description = "Invoice data transfer object")
public class InvoiceDto {
    @Schema(description = "Invoice identifier", example = "1")
    private Long id;

    @Schema(description = "Order ID", example = "1")
    private Long orderId;

    @Schema(description = "Invoice number", example = "INV-2024-001")
    private String invoiceNumber;

    @Schema(description = "Tax amount", example = "15.99")
    private BigDecimal taxAmount;

    @Schema(description = "Net amount", example = "199.99")
    private BigDecimal netAmount;

    @Schema(description = "Invoice date")
    private LocalDateTime invoiceDate;

    @Schema(description = "Invoice status")
    private Status status;
}
