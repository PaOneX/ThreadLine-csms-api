package edu.icet.service.impl;

import edu.icet.exception.SupplierNotFoundException;
import edu.icet.mapper.SupplierMapper;
import edu.icet.model.dto.SupplierDto;
import edu.icet.model.dto.SupplierRequestDto;
import edu.icet.model.entity.Supplier;
import edu.icet.repository.SupplierRepository;
import edu.icet.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    @Override
    @Transactional
    public void addSupplier(SupplierRequestDto supplierRequestDto) {
        repository.save(mapper.toEntity(supplierRequestDto));
    }

    @Override
    @Transactional
    public void updateSupplier(Long id, SupplierRequestDto supplierRequestDto) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
        mapper.updateEntityFromDto(supplierRequestDto, supplier);
        repository.save(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!repository.existsById(id)) {
            throw new SupplierNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierDto> getSuppliers() {
        List<Supplier> supplierList = repository.findAll();
        return mapper.toListDto(supplierList);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierDto getSupplierById(Long id) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
        return mapper.toDto(supplier);
    }
}
