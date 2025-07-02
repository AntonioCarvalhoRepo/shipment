package dachser.shipments.shipments.service;

import dachser.shipments.shipments.dto.FinancialDataResponseDTO;
import dachser.shipments.shipments.dto.ShipmentRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipmentService {
    void createFinancialData(ShipmentRequestDTO shipmentRequest);

    List<FinancialDataResponseDTO> getFinancialDataByShipmentId(String shipmentId);
}
