package edu.icet.service.impl;

import edu.icet.mapper.OrderItemMapper;
import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.OrderItemRepository;
import edu.icet.repository.OrdersRepository;
import edu.icet.repository.ProductVarientRpository;
import edu.icet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository repository;
    private final OrdersRepository ordersRepository;
    private final ProductVarientRpository variantRepository;
    private final OrderItemMapper mapper;

    @Override
    @Transactional
    public void addOrderItem(Long orderId, OrderItemRequestDto requestDto) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ProductVariant variant = variantRepository.findById(requestDto.getVariantId())
                .orElseThrow(() -> new RuntimeException("Product Variant not found"));

        OrderItem entity = mapper.toEntity(requestDto);
        entity.setOrder(order);
        entity.setVariant(variant);

        if (entity.getUnitPrice() == null) {
            entity.setUnitPrice(variant.getPrice());
        }

        repository.save(entity);
    }

    @Override
    @Transactional
    public void updateOrderItem(Long orderId, Long itemId, OrderItemRequestDto requestDto) {
        OrderItem orderItem = repository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("OrderItem does not belong to the specified order");
        }

        if (requestDto.getVariantId() != null &&
                !requestDto.getVariantId().equals(orderItem.getVariant().getId())) {
            ProductVariant variant = variantRepository.findById(requestDto.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Product Variant not found"));
            orderItem.setVariant(variant);
        }

        mapper.updateEntityFromDto(requestDto, orderItem);
        repository.save(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = repository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("OrderItem does not belong to the specified order");
        }

        repository.delete(orderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDto> getOrderItems(Long orderId) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> orderItemList = repository.findAllByOrder(order);
        return mapper.toDtoList(orderItemList);
    }
}
