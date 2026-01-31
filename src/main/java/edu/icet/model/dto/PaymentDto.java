package edu.icet.model.dto;

import edu.icet.util.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private Long id;
    private Long invoiceId;
    private long transactionId;
    private PaymentMode paymentMode;
    private Double amount;
    private Date payDate;
}
