package xyz.shipments.financial.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import xyz.shipments.financial.dto.CostRequestDTO;
import xyz.shipments.financial.dto.FinancialDataResponseDTO;
import xyz.shipments.financial.dto.IncomeRequestDTO;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import xyz.shipments.financial.mapper.FinancialMapper;
import xyz.shipments.financial.repository.FinancialDataRepository;
import xyz.shipments.financial.entity.FinancialData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private FinancialDataRepository financialDataRepository;

    @Mock
    private FinancialMapper financialMapper;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @Test
    /*
     * Test for creating financial data.
     * This test checks if the financial data is saved correctly in the repository.
     */
    void createFinancialData_CallsRepositorySave() {
        IncomeRequestDTO incomeRequestDTO = new IncomeRequestDTO();
        incomeRequestDTO.setValue(200.0);

        CostRequestDTO costRequestDTO = new CostRequestDTO();
        costRequestDTO.setCost(80.0);
        costRequestDTO.setAdditionalCost(20.0);

        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setShipmentID("0008"); // Use a unique ID for the test
        shipmentRequestDTO.setIncome(incomeRequestDTO);
        shipmentRequestDTO.setCosts(costRequestDTO);

        FinancialData mockedFinancialData = new FinancialData();
        when(financialMapper.mapShipmentRequestDTOtoEntity(any(ShipmentRequestDTO.class)))
                .thenReturn(mockedFinancialData);

        shipmentService.createFinancialData(shipmentRequestDTO);

        verify(financialDataRepository, times(1)).save(any(FinancialData.class));
    }

    @Test
    /*
     * Test for retrieving financial data by shipment ID.
     * This test checks if the service returns a list of FinancialDataResponseDTO when given a valid shipment ID.
     */
    void getFinancialDataByShipmentId_ReturnsDTOList() {
        String shipmentId = "0008";
        Double balance = 100.0;
        FinancialData financialData = new FinancialData();
        financialData.setShipmentID(shipmentId);
        financialData.setBalance(balance);

        FinancialDataResponseDTO responseDTO = new FinancialDataResponseDTO();
        responseDTO.setProfitOrLoss(balance);

        Pageable pageable = PageRequest.of(0, 10); // First page, 10 items per page

        // Mock the mapper to return the expected DTO
        when(financialMapper.mapFinancialDataToResponseDTO(any(FinancialData.class)))
                .thenReturn(responseDTO);

        // Mock repository to return a Page containing the financial data
        Page<FinancialData> financialDataPage = new PageImpl<>(List.of(financialData), pageable, 1);
        when(financialDataRepository.findByShipmentID(shipmentId, pageable)).thenReturn(financialDataPage);

        Page<FinancialDataResponseDTO> result = shipmentService.getFinancialDataByShipmentId(shipmentId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(balance, result.getContent().get(0).getProfitOrLoss());

    }
}