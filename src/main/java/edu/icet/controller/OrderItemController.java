package edu.icet.controller;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OrderItems")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService service;

    @GetMapping("/{id}")
    public List<OrderItemDto> findAll(@PathVariable Long id) {
        return service.getOrderItems(id);
    }

    @PostMapping
    public void save(@RequestBody OrderItemRequestDto requestDto) {
        service.addOrderItem(requestDto);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody OrderItemRequestDto requestDto) {
        service.updateOrderItem(id, requestDto);
    }
}
