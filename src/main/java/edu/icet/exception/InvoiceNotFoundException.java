package edu.icet.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Long id) {
        super("Invoice not found with id: " + id);
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
