package edu.icet.service;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    void addSupplier(SupplierDto supplierDto);
    void deleteSupplier(Long id);
    List<SupplierDto> getSuppliers();
}
