package edu.icet.controller;

import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.service.InventryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/")
@RequiredArgsConstructor

public class InventoryController {

    private final InventryService service;

    @GetMapping
    public List<InventoryDto> getAllInventory() {
        return service.getAllInventory();
    }

    @PostMapping
    public void addInventory(@RequestBody InventoryRequestDto requestDto) {
        service.addInventry(requestDto);
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDto requestDto) {
        service.updateInventory(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        service.deleteInventory(id);
    }
}
