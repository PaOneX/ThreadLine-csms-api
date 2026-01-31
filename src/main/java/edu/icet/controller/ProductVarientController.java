package edu.icet.controller;

import edu.icet.model.dto.ProductVarientDto;
import edu.icet.model.dto.ProductvarientRequestDto;
import edu.icet.service.ProductVarientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productVarients")
@RequiredArgsConstructor
public class ProductVarientController {
    private final ProductVarientService service;

    @GetMapping
    public List<ProductVarientDto> getProductVarients(){
        return service.findProductVarient();
    }

    @GetMapping("/{id}")
    public ProductVarientDto getProductVariantById(@PathVariable Long id){
        return service.findProductById(id);
    }

    @GetMapping("colors/{color}")
    public List<ProductVarientDto> getProductVariantsByColor(@PathVariable String color){
        return service.findProductByColor(color);
    }

    @GetMapping("sizes/{size}")
    public List<ProductVarientDto> getProductVariantsBySize(@PathVariable String size){
        return service.findProductBySize(size);
    }

    @PostMapping
    public void addProductVarient(@RequestBody ProductvarientRequestDto requestDto){
        service.addProductVarient(requestDto);
    }

    @PutMapping("/{id}")
    public void updateProductVarient(@PathVariable Long id,@RequestBody ProductvarientRequestDto requestDto){
        service.updateProductVarient(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductVarient(@PathVariable Long id){
        service.deleteProductVarient(id);
    }

}
