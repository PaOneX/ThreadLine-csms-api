package edu.icet.model.dto;

import edu.icet.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceRequestDto {
        private String invoiceNumber;
        private Double taxAmount;
        private Double netAmount;
        private Status status;



}

