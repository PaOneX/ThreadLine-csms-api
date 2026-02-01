package edu.icet.controller;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService service;

    @GetMapping
    public List<OrderItemDto> findAll(@PathVariable Long orderId) {
        return service.getOrderItems(orderId);
    }

    @PostMapping
    public void save(@PathVariable Long orderId, @RequestBody OrderItemRequestDto requestDto) {
        service.addOrderItem(orderId ,requestDto);
    }

    @PutMapping("/{itemId}")
    public void update(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody OrderItemRequestDto requestDto) {
        service.updateOrderItem(orderId, itemId, requestDto);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@PathVariable Long orderId, @PathVariable Long itemId) {
        service.deleteOrderItem(orderId,itemId);
    }
}
