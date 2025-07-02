package dachser.shipments.shipments.service;

import dachser.shipments.shipments.dto.FinancialDataResponseDTO;
import dachser.shipments.shipments.dto.ShipmentRequestDTO;
import dachser.shipments.shipments.entity.FinancialData;
import dachser.shipments.shipments.exception.ShipmentNotFoundException;
import dachser.shipments.shipments.mapper.FinancialMapper;
import dachser.shipments.shipments.repository.FinancialDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ShipmentServiceImpl implements ShipmentService {
    private final FinancialMapper financialMapper;
    private final FinancialDataRepository financialDataRepository;


    public ShipmentServiceImpl(FinancialMapper financialMapper, FinancialDataRepository financialDataRepository)
    {
        this.financialMapper = financialMapper;
        this.financialDataRepository = financialDataRepository;
    }

    @Override
    @CacheEvict(value = "financialDataCache", key = "#shipmentRequest.shipmentID")
    public void createFinancialData(ShipmentRequestDTO shipmentRequest) {
        financialDataRepository.save(financialMapper.mapShipmentRequestDTOtoEntity(shipmentRequest));
    }

    @Override
    @Cacheable(value = "financialDataCache", key = "#shipmentId", unless = "#result == null")
    public List<FinancialDataResponseDTO> getFinancialDataByShipmentId(String shipmentId) {
        log.debug("Fetching financial data for shipment ID: {}", shipmentId);
        List<FinancialData> financialData = financialDataRepository.findByShipmentID(shipmentId);

        log.debug("Found {} financial records for shipment ID: {}", financialData.size(), shipmentId);
        if (financialData.isEmpty()) {
            throw new ShipmentNotFoundException("No financial data found for shipment ID: " + shipmentId);
        }
        return financialData.stream()
                .map(financialMapper::mapFinancialDataToResponseDTO)
                .toList();

    }
}
