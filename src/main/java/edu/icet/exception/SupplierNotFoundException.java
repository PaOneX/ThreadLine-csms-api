package edu.icet.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Long id) {
        super("Supplier not found with id: " + id);
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }
}
