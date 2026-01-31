package edu.icet.model.dto;

import edu.icet.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceDto {
    private Long id;
    private String invoiceNumber;
    private Double taxAmount;
    private Double netAmount;
    private Date invoiceDate;
    private Status status;
}
