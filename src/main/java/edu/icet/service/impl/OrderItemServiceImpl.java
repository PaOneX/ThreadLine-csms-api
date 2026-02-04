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
    public void addOrderItem(Long orderId, OrderItemRequestDto requestDto) {
        OrderItemEntity entity = mapper.toEntity(requestDto);
        entity.setOrderId(orderId);
        repository.save(entity);
    }

    @Override
    public void updateOrderItem(Long orderId, Long itemId, OrderItemRequestDto requestDto) {
        OrderItemEntity orderItem = repository.findById(orderId).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        orderItem.setPrice(requestDto.getPrice());
        orderItem.setQuantity(requestDto.getQuantity());
        repository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id, Long itemId) {
        OrderItemEntity orderItem = repository.findById(itemId).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        if (orderItem.getOrderId().equals(id)) {
            repository.delete(orderItem);
        } else {
            throw new RuntimeException("OrderItem not found");
        }
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        List<OrderItemEntity> orderItemEntityList = repository.findAllByOrderId(orderId);
        return mapper.toDtoList(orderItemEntityList);
    }
}
