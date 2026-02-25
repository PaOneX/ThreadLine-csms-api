package edu.icet.model.entity;

import edu.icet.model.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order"})
public class Invoice {
    private Long id;

    private Order order;

    private String invoiceNumber;

    private BigDecimal taxAmount;

    private BigDecimal netAmount;

    private LocalDateTime invoiceDate;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (invoiceDate == null) {
            invoiceDate = LocalDateTime.now();
        }
        if (status == null) {
            status = Status.PENDING;
        }
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
