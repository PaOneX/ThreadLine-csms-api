package edu.icet.service.impl;

import edu.icet.mapper.InvoiceMapper;
import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;
import edu.icet.model.entity.InvoiceEntity;
import edu.icet.repository.InvoiceRepository;
import edu.icet.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;
    @Override
    public void createInvoice(InvoiceRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updateInvoice(Long id, InvoiceRequestDto requestDto) {
        InvoiceEntity invoiceEntity = repository.findById(id).get();
        invoiceEntity.setInvoiceNumber(requestDto.getInvoiceNumber());
        invoiceEntity.setTaxAmount(requestDto.getTaxAmount());
        invoiceEntity.setNetAmount(requestDto.getNetAmount());
        invoiceEntity.setStatus(requestDto.getStatus());
        repository.save(invoiceEntity);

    }

    @Override
    public void deleteInvoice(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<InvoiceDto> getInvoices() {
        List<InvoiceEntity> invoiceEntity = repository.findAll();
        return mapper.toDtoList(invoiceEntity);
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        InvoiceEntity invoiceEntity = repository.findById(id).get();
        return mapper.toDto(invoiceEntity);
    }
}
