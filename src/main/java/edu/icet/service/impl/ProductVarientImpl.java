package edu.icet.service.impl;

import edu.icet.exception.ProductNotFoundException;
import edu.icet.mapper.ProductVarientMapper;
import edu.icet.model.dto.ProductVarientDto;
import edu.icet.model.dto.ProductvarientRequestDto;
import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.ProductRepository;
import edu.icet.repository.ProductVarientRpository;
import edu.icet.service.ProductVarientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVarientImpl implements ProductVarientService {
    private final ProductVarientRpository repository;
    private final ProductRepository productRepository;
    private final ProductVarientMapper mapper;

    @Override
    @Transactional
    public void addProductVarient(ProductvarientRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(requestDto.getProductId()));

        ProductVariant variant = mapper.toEntity(requestDto);
        variant.setProduct(product);
        repository.save(variant);
    }

    @Override
    @Transactional
    public void updateProductVarient(Long id, ProductvarientRequestDto requestDto) {
        ProductVariant productVariant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Variant Not Found"));

        if (requestDto.getProductId() != null &&
                !requestDto.getProductId().equals(productVariant.getProduct().getId())) {
            Product product = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(requestDto.getProductId()));
            productVariant.setProduct(product);
        }

        mapper.updateEntityFromDto(requestDto, productVariant);
        repository.save(productVariant);
    }

    @Override
    public void deleteProductVarient(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product Variant Not Found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVarientDto> findProductVarient() {
        List<ProductVariant> productVariant = repository.findAll();
        return mapper.toDtoList(productVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVarientDto findProductById(Long id) {
        ProductVariant productVariant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Variant Not Found"));
        return mapper.toDto(productVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVarientDto> findProductBySize(String size) {
        List<ProductVariant> productVariant = repository.findBySize(size);
        return mapper.toDtoList(productVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVarientDto> findProductByColor(String color) {
        List<ProductVariant> productVariant = repository.findByColor(color);
        return mapper.toDtoList(productVariant);
    }
}
