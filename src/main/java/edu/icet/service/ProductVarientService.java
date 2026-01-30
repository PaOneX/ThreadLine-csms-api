package edu.icet.service;

import edu.icet.model.dto.ProductVarientDto;
import edu.icet.model.dto.ProductvarientRequestDto;

import java.util.List;

public interface ProductVarientService {
    void addProductVarient(ProductvarientRequestDto requestDto);
    void updateProductVarient(Long id,ProductvarientRequestDto requestDto);
    void deleteProductVarient(Long id);
    List<ProductVarientDto> findProductVarient();
    ProductVarientDto findProductById(Long id);
    ProductVarientDto findProductBySize(String color);
    ProductVarientDto findProductByColor(String colo);
}
