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
    List<ProductVarientDto> findProductBySize(String color);
    List<ProductVarientDto> findProductByColor(String colo);
}
