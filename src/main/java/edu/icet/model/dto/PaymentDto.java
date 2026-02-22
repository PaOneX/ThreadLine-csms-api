package edu.icet.model.dto;

import edu.icet.model.enums.PaymentMode;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "PaymentDto", description = "Payment data transfer object")
public class PaymentDto {
//    @Schema(description = "Payment identifier", example = "1")
    private Long id;

//    @Schema(description = "Invoice ID", example = "1")
    private Long invoiceId;

//    @Schema(description = "Invoice number", example = "INV-2024-001")
    private String invoiceNumber;

//    @Schema(description = "Transaction ID", example = "TXN-123456")
    private String transactionId;

//    @Schema(description = "Payment mode")
    private PaymentMode paymentMode;

//    @Schema(description = "Payment amount", example = "199.99")
    private BigDecimal amount;

//    @Schema(description = "Payment date")
    private LocalDateTime paymentDate;
}
