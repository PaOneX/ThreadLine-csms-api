package edu.icet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ProductVarientDto {
    private Long id;
    private Long productId;
    private String size;
    private String color;
    private Double price;
}

