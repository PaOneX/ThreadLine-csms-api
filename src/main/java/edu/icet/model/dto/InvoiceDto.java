package edu.icet.model.dto;

import edu.icet.util.Status;

import java.util.Date;

public class InvoiceDto {
    private Long id;
    private String invoiceNumber;
    private Double taxAmount;
    private Double netAmount;
    private Date invoiceDate;
    private Status status;
}
