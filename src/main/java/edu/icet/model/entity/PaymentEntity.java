package edu.icet.model.entity;

import edu.icet.util.PaymentMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long invoiceId;
    private Long transactionId;
    private PaymentMode paymentMode;
    private Double amount;
    private Date payDate;

    @PrePersist
    public void date(){
        payDate = new Date();
    }
}
