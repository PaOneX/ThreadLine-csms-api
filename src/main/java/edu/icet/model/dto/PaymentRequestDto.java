package edu.icet.model.dto;

import edu.icet.util.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentRequestDto {

    private Long invoiceId;
    private long transactionId;
    private PaymentMode paymentMode;
    private Double amount;

}
