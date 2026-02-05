package edu.icet.service.impl;

import edu.icet.mapper.InvoiceMapper;
import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;
import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import edu.icet.repository.InvoiceRepository;
import edu.icet.repository.OrdersRepository;
import edu.icet.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repository;
    private final OrdersRepository ordersRepository;
    private final InvoiceMapper mapper;

    @Override
    @Transactional
    public void createInvoice(InvoiceRequestDto requestDto) {
        Order order = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Invoice invoice = mapper.toEntity(requestDto);
        invoice.setOrder(order);

        // Generate invoice number if not provided
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        repository.save(invoice);
    }

    @Override
    @Transactional
    public void updateInvoice(Long id, InvoiceRequestDto requestDto) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (requestDto.getOrderId() != null &&
                !requestDto.getOrderId().equals(invoice.getOrder().getId())) {
            Order order = ordersRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            invoice.setOrder(order);
        }

        mapper.updateEntityFromDto(requestDto, invoice);
        repository.save(invoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Invoice not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDto> getInvoices() {
        List<Invoice> invoices = repository.findAll();
        return mapper.toDtoList(invoices);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapper.toDto(invoice);
    }
}
