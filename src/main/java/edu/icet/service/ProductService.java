package edu.icet.service;

import edu.icet.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();

    ProductDto getProductById(Long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}
