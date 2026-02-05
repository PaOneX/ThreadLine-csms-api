package edu.icet.service.impl;

import edu.icet.exception.InventoryNotFoundException;
import edu.icet.exception.ProductVariantNotFoundException;
import edu.icet.mapper.InventoryMapper;
import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.InventoryRepository;
import edu.icet.repository.ProductVarientRpository;
import edu.icet.service.InventryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventryService {

    private final InventoryRepository repository;
    private final ProductVarientRpository variantRepository;
    private final InventoryMapper mapper;

    @Override
    @Transactional
    public void addInventry(InventoryRequestDto requestDto) {
        ProductVariant variant = variantRepository.findById(requestDto.getVariantId())
                .orElseThrow(() -> new ProductVariantNotFoundException(requestDto.getVariantId()));

        Inventory inventory = mapper.toEntity(requestDto);
        inventory.setVariant(variant);
        inventory.setLastRestocked(LocalDateTime.now());
        repository.save(inventory);
    }

    @Override
    @Transactional
    public void updateInventory(Long id, InventoryRequestDto requestDto) {
        Inventory entity = repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));

        if (requestDto.getVariantId() != null &&
                !requestDto.getVariantId().equals(entity.getVariant().getId())) {
            ProductVariant variant = variantRepository.findById(requestDto.getVariantId())
                    .orElseThrow(() -> new ProductVariantNotFoundException(requestDto.getVariantId()));
            entity.setVariant(variant);
        }

        entity.setQuantity(requestDto.getQuantity());
        entity.setLastRestocked(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void deleteInventory(Long id) {
        if (!repository.existsById(id)) {
            throw new InventoryNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDto> getAllInventory() {
        List<Inventory> list = repository.findAll();
        return mapper.toDto(list);
    }
}
