package edu.icet.service.impl;

import edu.icet.mapper.SupplierMapper;
import edu.icet.model.dto.SupplierDto;
import edu.icet.model.entity.SupplierEntity;
import edu.icet.repository.SupplierRepository;
import edu.icet.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;
    private final SupplierMapper mapper;
    @Override
    public void addSupplier(SupplierDto supplierDto) {
        repository.save(mapper.toEntity(supplierDto));
    }

    @Override
    public void deleteSupplier(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SupplierDto> getSuppliers() {
        List<SupplierEntity> supplierEntityList = repository.findAll();
        return mapper.toListDto(supplierEntityList);
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        SupplierEntity supplierEntity = repository.findById(id).get();
        return mapper.toDto(supplierEntity);
    }
}
