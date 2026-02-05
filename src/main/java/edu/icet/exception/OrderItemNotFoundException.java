package edu.icet.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(Long id) {
        super("Order Item not found with id: " + id);
    }

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
