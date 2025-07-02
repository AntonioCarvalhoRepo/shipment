package xyz.shipments.financial.service;

import xyz.shipments.financial.dto.FinancialDataResponseDTO;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipmentService {
    void createFinancialData(ShipmentRequestDTO shipmentRequest);

    List<FinancialDataResponseDTO> getFinancialDataByShipmentId(String shipmentId);
}
