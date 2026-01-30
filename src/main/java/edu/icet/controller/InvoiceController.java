package edu.icet.controller;

import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;
import edu.icet.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;

    @GetMapping
    public List<InvoiceDto> getInvoices() {
        return service.getInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceDto getInvoice(@PathVariable Long id) {
        return service.getInvoiceById(id);
    }

    @PostMapping
    public void createInvoice(@RequestBody InvoiceRequestDto requestDto) {
        service.createInvoice(requestDto);
    }

    @PutMapping("/{id}")
    public void updateInvoice(@PathVariable Long id,@RequestBody InvoiceRequestDto requestDto) {
        service.updateInvoice(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        service.deleteInvoice(id);
    }
}
