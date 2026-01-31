package edu.icet.service;

import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;

import java.util.List;

public interface InvoiceService {
    void createInvoice(InvoiceRequestDto requestDto);
    void updateInvoice(Long id , InvoiceRequestDto requestDto);
    void deleteInvoice(Long id);
    List<InvoiceDto> getInvoices();
    InvoiceDto getInvoiceById(Long id);
}
