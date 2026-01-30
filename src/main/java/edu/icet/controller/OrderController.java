package edu.icet.controller;

import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import edu.icet.model.entity.OrderEntity;
import edu.icet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrdersDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrdersDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public void save(@RequestBody OrderRequestDto orderRequestDto) {
        service.placeOrder(orderRequestDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody OrderRequestDto orderRequestDto) {
        service.updateOrder(id, orderRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteOrder(id);
    }
}
