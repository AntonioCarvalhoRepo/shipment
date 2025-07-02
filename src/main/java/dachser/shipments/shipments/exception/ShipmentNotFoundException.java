package dachser.shipments.shipments.exception;

public class ShipmentNotFoundException extends RuntimeException{
    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
