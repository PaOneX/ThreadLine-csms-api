package edu.icet.controller;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.entity.SupplierEntity;
import edu.icet.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier/")
public class SupplierController {
    private final SupplierService service;

    @GetMapping
    public List<SupplierDto> getSuppliers() {
        return service.getSuppliers();
    }

    @GetMapping("{id}")
    public SupplierDto getSupplier(@PathVariable Long id) {
       return service.getSupplierById(id);
    }

    @PostMapping
    public void addSupplier(@RequestBody SupplierDto supplierDto) {
        service.addSupplier(supplierDto);
    }

    @DeleteMapping("{id}")
    public void deleteSupplier(@PathVariable Long id) {
        service.deleteSupplier(id);
    }
}
