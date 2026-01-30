package edu.icet.model.entity;

import edu.icet.util.Status;
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
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private Double taxAmount;
    private Double netAmount;
    private Date invoiceDate;
    private Status status;

    @PrePersist
    public void invoiceDate(){
        invoiceDate = new Date();
    }

}
