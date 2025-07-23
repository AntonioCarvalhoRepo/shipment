package xyz.shipments.financial;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import xyz.shipments.financial.controller.FinancialDataController;
import xyz.shipments.financial.dto.CostRequestDTO;
import xyz.shipments.financial.dto.FinancialDataResponseDTO;
import xyz.shipments.financial.dto.IncomeRequestDTO;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import xyz.shipments.financial.service.ShipmentService;
import xyz.shipments.financial.repository.FinancialDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(FinancialDataController.class)
class FinancialDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @MockBean
    private FinancialDataRepository financialDataRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    /*
     * Test for creating financial data.
     * This test checks if the endpoint returns a 201 Created status when valid data is provided.
     */
    void createFinancialData_ReturnsCreated() throws Exception {
        IncomeRequestDTO incomeRequestDTO = new IncomeRequestDTO();
        incomeRequestDTO.setValue(100.0);

        CostRequestDTO costRequestDTO = new CostRequestDTO();
        costRequestDTO.setCost(50.0);
        costRequestDTO.setAdditionalCost(100.0);

        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setShipmentID("123");
        shipmentRequestDTO.setIncome(incomeRequestDTO);
        shipmentRequestDTO.setCosts(costRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/financialData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(shipmentRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getFinancialDataByShipmentId_ReturnsOk_WithPageable() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FinancialDataResponseDTO> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        Mockito.when(shipmentService.getFinancialDataByShipmentId(ArgumentMatchers.eq("123"), ArgumentMatchers.eq(pageable)))
                .thenReturn(emptyPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/financialData/123")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}