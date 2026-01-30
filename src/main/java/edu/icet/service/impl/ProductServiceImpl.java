package edu.icet.service.impl;

import edu.icet.exception.CategoryNotFoundException;
import edu.icet.exception.ProductNotFoundException;
import edu.icet.mapper.ProductMapper;
import edu.icet.model.dto.ProductDto;
import edu.icet.model.entity.Category;
import edu.icet.model.entity.Product;
import edu.icet.repository.CategoryRepository;
import edu.icet.repository.ProductRepository;
import edu.icet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        List<Product> productList = productRepository.findAll();
        return productMapper.toDtoList(productList);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        if (productDto.getCategory() != null && productDto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory().getId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        if (productDto.getCategory() != null && productDto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory().getId()));
            existingProduct.setCategory(category);
        }
        productMapper.updateEntityFromDto(productDto, existingProduct);
        Product saved = productRepository.save(existingProduct);
        return productMapper.toDto(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
