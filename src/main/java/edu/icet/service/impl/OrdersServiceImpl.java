package edu.icet.service.impl;

import edu.icet.mapper.OrderMapper;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;
import edu.icet.model.entity.User;
import edu.icet.repository.OrdersRepository;
import edu.icet.repository.ProductVarientRpository;
import edu.icet.repository.UserRepository;
import edu.icet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrderService {

    private final OrdersRepository repository;
    private final UserRepository userRepository;
    private final ProductVarientRpository variantRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order order = mapper.toEntity(orderRequestDto);

        // Set user if provided
        if (orderRequestDto.getUserId() != null) {
            User user = userRepository.findById(orderRequestDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        // Process order items
        if (orderRequestDto.getItems() != null && !orderRequestDto.getItems().isEmpty()) {
            List<OrderItem> items = new ArrayList<>();
            for (OrderItemRequestDto itemDto : orderRequestDto.getItems()) {
                ProductVariant variant = variantRepository.findById(itemDto.getVariantId())
                        .orElseThrow(() -> new RuntimeException("Product Variant not found: " + itemDto.getVariantId()));

                OrderItem item = OrderItem.builder()
                        .order(order)
                        .variant(variant)
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice() != null ? itemDto.getUnitPrice() : variant.getPrice())
                        .build();
                items.add(item);
            }
            order.setItems(items);
        }

        repository.save(order);
    }

    @Override
    @Transactional
    public void updateOrder(Long id, OrderRequestDto orderRequestDto) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderRequestDto.getUserId() != null) {
            User user = userRepository.findById(orderRequestDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        if (orderRequestDto.getStatus() != null) {
            order.setStatus(orderRequestDto.getStatus());
        }

        if (orderRequestDto.getTotalAmount() != null) {
            order.setTotalAmount(orderRequestDto.getTotalAmount());
        }

        repository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdersDto> findAll() {
        List<Order> orderList = repository.findAll();
        return mapper.toDtoList(orderList);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdersDto findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.toDto(order);
    }
}
