package edu.icet.service;

import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;

import java.util.List;

public interface InventryService {

    void addInventry(InventoryRequestDto requestDto);

    void updateInventory(Long id, InventoryRequestDto requestDto);

    void deleteInventory(Long id);

    List<InventoryDto> getAllInventory();

}
