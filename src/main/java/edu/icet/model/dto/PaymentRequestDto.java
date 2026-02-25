package edu.icet.model.dto;

import edu.icet.model.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "PaymentRequestDto", description = "Payment create/update request")
public class PaymentRequestDto {
    //    @NotNull(message = "invoiceId must not be null")
//    @Schema(description = "Invoice ID", example = "1", required = true)
    private Long invoiceId;

    //    @Schema(description = "Transaction ID", example = "TXN-123456")
    private String transactionId;

    //    @NotNull(message = "paymentMode must not be null")
//    @Schema(description = "Payment mode", required = true)
    private PaymentMode paymentMode;

    //    @NotNull(message = "amount must not be null")
//    @Positive(message = "amount must be positive")
//    @Schema(description = "Payment amount", example = "199.99", required = true)
    private BigDecimal amount;
}
