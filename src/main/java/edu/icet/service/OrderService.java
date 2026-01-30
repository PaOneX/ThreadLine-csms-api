package edu.icet.service;

import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import java.util.List;

public interface OrderService {
    void placeOrder(OrderRequestDto orderRequestDto);
    void updateOrder(Long id, OrderRequestDto orderRequestDto);
    void deleteOrder(Long id);
    List<OrdersDto> findAll();
    OrdersDto findById(Long id);
}
