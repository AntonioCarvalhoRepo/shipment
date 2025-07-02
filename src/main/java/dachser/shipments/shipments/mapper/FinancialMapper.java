package dachser.shipments.shipments.mapper;

import dachser.shipments.shipments.dto.FinancialDataResponseDTO;
import dachser.shipments.shipments.dto.ShipmentRequestDTO;
import dachser.shipments.shipments.entity.FinancialData;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class FinancialMapper {
    public FinancialData mapShipmentRequestDTOtoEntity (ShipmentRequestDTO shipmentRequestDTO){
        FinancialData data = new FinancialData();
        data.setShipmentID(shipmentRequestDTO.getShipmentID());
        data.setIncome(shipmentRequestDTO.getIncome().getValue());
        data.setCosts(shipmentRequestDTO.getCosts().getCost() + Optional.of(shipmentRequestDTO.getCosts().getAdditionalCost()).orElse(0.0));
        data.setBalance(data.getIncome() - data.getCosts());
        data.setCreated(Date.from(Instant.now()));
        data.setModified(Date.from(Instant.now()));
        return  data;
    }

    public FinancialDataResponseDTO mapFinancialDataToResponseDTO(FinancialData financialData) {
        FinancialDataResponseDTO financialDataResponseDTO = new FinancialDataResponseDTO();
        financialDataResponseDTO.setIncome(financialData.getIncome());
        financialDataResponseDTO.setTotalCosts(financialData.getCosts());
        financialDataResponseDTO.setProfitOrLoss(financialData.getBalance());
        return financialDataResponseDTO;
    }
}
