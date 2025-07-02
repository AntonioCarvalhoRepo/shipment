package dachser.shipments.shipments;

import com.fasterxml.jackson.databind.ObjectMapper;
import dachser.shipments.shipments.dto.CostRequestDTO;
import dachser.shipments.shipments.dto.IncomeRequestDTO;
import dachser.shipments.shipments.dto.ShipmentRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FinancialDataControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    /* * Integration test for creating financial data and retrieving it by shipment ID.
     * This test simulates a full cycle of creating financial data and then fetching it.
     * Since it uses the actual database, it is marked as an integration test.
     */
    void createAndGetFinancialData_IntegrationTest() throws Exception {
        IncomeRequestDTO incomeRequestDTO = new IncomeRequestDTO();
        incomeRequestDTO.setValue(200.0);

        CostRequestDTO costRequestDTO = new CostRequestDTO();
        costRequestDTO.setCost(80.0);
        costRequestDTO.setAdditionalCost(20.0);

        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setShipmentID("0008"); // Use a unique ID for the test
        shipmentRequestDTO.setIncome(incomeRequestDTO);
        shipmentRequestDTO.setCosts(costRequestDTO);

        // POST /financial
        mockMvc.perform(post("/financialData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(shipmentRequestDTO)))
                .andExpect(status().isCreated());

        // GET /financial/INTEGRATION123
        mockMvc.perform(get("/financialData/0008"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}