package edu.icet.service.impl;

import edu.icet.mapper.InventoryMapper;
import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.model.entity.InventoryEntity;
import edu.icet.repository.InventoryRepository;
import edu.icet.service.InventryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    @Override
    public void addInventry(InventoryRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updateInventory(Long id, InventoryRequestDto requestDto) {
        InventoryEntity entity = repository.findById(id).get();
        entity.setQuantity(requestDto.getQuantity());
        repository.save(entity);
    }

    @Override
    public void deleteInventory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<InventoryDto> getAllInventory() {
        List<InventoryEntity> list = repository.findAll();
        return mapper.toDto(list);
    }
}
