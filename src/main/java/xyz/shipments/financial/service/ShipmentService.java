package xyz.shipments.financial.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.shipments.financial.dto.FinancialDataResponseDTO;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShipmentService {
    void createFinancialData(ShipmentRequestDTO shipmentRequest);

    Page<FinancialDataResponseDTO> getFinancialDataByShipmentId(String shipmentId, Pageable pageable);
}
