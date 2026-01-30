package edu.icet.service.impl;

import edu.icet.mapper.ProductVarientMapper;
import edu.icet.model.dto.ProductVarientDto;
import edu.icet.model.dto.ProductvarientRequestDto;
import edu.icet.model.entity.ProductVarientEntity;
import edu.icet.repository.ProductVarientRpository;
import edu.icet.service.ProductVarientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVarientImpl implements ProductVarientService {
    private final ProductVarientRpository repository;
    private final ProductVarientMapper mapper;
    @Override
    public void addProductVarient(ProductvarientRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updateProductVarient(Long id, ProductvarientRequestDto requestDto) {
        ProductVarientEntity productVarient = repository.findById(id).orElseThrow(() -> new RuntimeException("Product Varient Not Found"));
        productVarient.setColor(requestDto.getColor());
        productVarient.setSize(requestDto.getSize());
        productVarient.setPrice(requestDto.getPrice());
        repository.save(productVarient);
    }

    @Override
    public void deleteProductVarient(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductVarientDto> findProductVarient() {
        List<ProductVarientEntity> productVarient = repository.findAll();
        return mapper.toDtoList(productVarient);
    }

    @Override
    public ProductVarientDto findProductById(Long id) {
        ProductVarientEntity productVarient = repository.findById(id).orElseThrow(() -> new RuntimeException("Product Varient Not Found"));
        return mapper.toDto(productVarient);
    }

    @Override
    public ProductVarientDto findProductBySize(String size) {
        ProductVarientEntity productVarient = repository.findProductByColor(size);
        return mapper.toDto(productVarient);
    }

    @Override
    public ProductVarientDto findProductByColor(String color) {
        ProductVarientEntity productVarient = repository.findProductByColor(color);
        return mapper.toDto(productVarient);
    }
}
