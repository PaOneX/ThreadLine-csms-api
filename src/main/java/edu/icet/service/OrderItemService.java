package edu.icet.service;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;

import java.util.List;

public interface OrderItemService {
    void addOrderItem(OrderItemRequestDto requestDto);
    void updateOrderItem(Long id, OrderItemRequestDto requestDto);
    void deleteOrderItem(Long id);
    List<OrderItemDto> getOrderItems(Long orderId);
}
