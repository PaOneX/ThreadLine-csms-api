package edu.icet.service.impl;

import edu.icet.mapper.OrderMapper;
import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import edu.icet.model.entity.OrderEntity;
import edu.icet.repository.OrdersRepository;
import edu.icet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrderService {

    private final OrdersRepository repository;
    private final OrderMapper mapper;

    @Override
    public void placeOrder(OrderRequestDto orderRequestDto) {
        repository.save(mapper.toEntity(orderRequestDto));
    }

    @Override
    public void updateOrder(Long id, OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = repository.findById(id).get();
        orderEntity.setUserId(orderRequestDto.getUserId());
        orderEntity.setStatus(orderRequestDto.getStatus());
        orderEntity.setOrderAmount(orderRequestDto.getOrderAmount());
        orderEntity.setOrderDate(orderEntity.getOrderDate());
        repository.save(orderEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrdersDto> findAll() {
        List<OrderEntity> orderEntityList = repository.findAll();
        return mapper.toDtoList(orderEntityList);
    }

    @Override
    public OrdersDto findById(Long id) {
        OrderEntity orderEntity = repository.findById(id).get();
        return mapper.toDto(orderEntity);
    }
}
