package edu.icet.exception;

public class ProductVariantNotFoundException extends RuntimeException {
    public ProductVariantNotFoundException(Long id) {
        super("Product Variant not found with id: " + id);
    }

    public ProductVariantNotFoundException(String message) {
        super(message);
    }
}
