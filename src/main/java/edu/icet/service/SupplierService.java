package edu.icet.service;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.dto.SupplierRequestDto;

import java.util.List;

public interface SupplierService {
    void addSupplier(SupplierRequestDto supplierRequestDto);

    void deleteSupplier(Long id);

    List<SupplierDto> getSuppliers();

    SupplierDto getSupplierById(Long id);
}
