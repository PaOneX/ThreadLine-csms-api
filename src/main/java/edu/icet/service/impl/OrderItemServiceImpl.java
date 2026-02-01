package edu.icet.service.impl;

import edu.icet.mapper.OrderItemMapper;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.repository.OrderItemRepository;
import edu.icet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper mapper;
    @Override
    public void addOrderItem(OrderItemRequestDto requestDto) {

    }

    @Override
    public void updateOrderItem(Long id, OrderItemRequestDto requestDto) {

    }

    @Override
    public void deleteOrderItem(Long id) {

    }

    @Override
    public List<OrderItemRequestDto> getOrderItems(Long orderId) {
        return List.of();
    }
}
