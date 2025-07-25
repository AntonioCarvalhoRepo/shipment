package xyz.shipments.financial.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import xyz.shipments.financial.dto.FinancialDataResponseDTO;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import xyz.shipments.financial.entity.FinancialData;
import xyz.shipments.financial.exception.ShipmentNotFoundException;
import xyz.shipments.financial.mapper.FinancialMapper;
import xyz.shipments.financial.repository.FinancialDataRepository;
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
    @Transactional
    @CacheEvict(value = "financialDataCache", key = "#shipmentRequest.shipmentID")
    public void createFinancialData(ShipmentRequestDTO shipmentRequest) {
        financialDataRepository.save(financialMapper.mapShipmentRequestDTOtoEntity(shipmentRequest));
    }

    @Override
    @Cacheable(value = "financialDataCache", key = "#shipmentId", unless = "#result == null")
    public Page<FinancialDataResponseDTO> getFinancialDataByShipmentId(String shipmentId, Pageable pageable) {
        log.debug("Fetching financial data for shipment ID: {}", shipmentId);
        Page<FinancialData> financialData = financialDataRepository.findByShipmentID(shipmentId,pageable);

        log.debug("Found {} financial records for shipment ID: {}", financialData.stream().count(), shipmentId);
        if (financialData.isEmpty()) {
            throw new ShipmentNotFoundException("No financial data found for shipment ID: " + shipmentId);
        }
        return new PageImpl<>(financialData.stream()
                .map(financialMapper::mapFinancialDataToResponseDTO)
                .toList(),pageable,financialData.stream().count());
    }
}
