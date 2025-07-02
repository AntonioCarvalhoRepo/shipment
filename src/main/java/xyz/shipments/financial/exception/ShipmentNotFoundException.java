package xyz.shipments.financial.exception;

public class ShipmentNotFoundException extends RuntimeException{
    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
