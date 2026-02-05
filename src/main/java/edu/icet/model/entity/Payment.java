package edu.icet.model.entity;

import edu.icet.model.enums.PaymentMode;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"invoice"})
public class Payment {
    private Long id;

    private Invoice invoice;

    private String transactionId;

    private PaymentMode paymentMode;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private LocalDateTime createdAt;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }
}
