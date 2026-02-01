package edu.icet.service.impl;

import edu.icet.mapper.OrderItemMapper;
import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.model.entity.OrderItemEntity;
import edu.icet.repository.OrderItemRepository;
import edu.icet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;
    @Override
    public void addOrderItem(OrderItemRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updateOrderItem(Long id, OrderItemRequestDto requestDto) {
        OrderItemEntity orderItem = repository.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        orderItem.setPrice(requestDto.getPrice());
        orderItem.setQuantity(requestDto.getQuantity());
        orderItem.setOrderId(requestDto.getOrderId());
        repository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        List<OrderItemEntity> orderItemEntityList =repository.findAllByOrderId(orderId);
        return mapper.toDtoList(orderItemEntityList);
    }
}
