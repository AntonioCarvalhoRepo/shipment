package dachser.shipments.shipments;

import com.fasterxml.jackson.databind.ObjectMapper;
import dachser.shipments.shipments.controller.FinancialDataController;
import dachser.shipments.shipments.dto.CostRequestDTO;
import dachser.shipments.shipments.dto.IncomeRequestDTO;
import dachser.shipments.shipments.dto.ShipmentRequestDTO;
import dachser.shipments.shipments.service.ShipmentService;
import dachser.shipments.shipments.repository.FinancialDataRepository;
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
    /* Test for retrieving financial data by shipment ID.
     * This test checks if the endpoint returns a 200 OK status when valid shipment ID is provided.
     */
    void getFinancialDataByShipmentId_ReturnsOk() throws Exception {
        Mockito.when(shipmentService.getFinancialDataByShipmentId(ArgumentMatchers.eq("123")))
                .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/financialData/123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}