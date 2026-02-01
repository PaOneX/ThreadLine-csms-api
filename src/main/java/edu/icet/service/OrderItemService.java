package edu.icet.service;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;

import java.util.List;

public interface OrderItemService {
    void addOrderItem(Long orderId, OrderItemRequestDto requestDto);
    void updateOrderItem(Long orderId, Long itemId, OrderItemRequestDto requestDto);
    void deleteOrderItem(Long id, Long itemId);
    List<OrderItemDto> getOrderItems(Long orderId);
}
