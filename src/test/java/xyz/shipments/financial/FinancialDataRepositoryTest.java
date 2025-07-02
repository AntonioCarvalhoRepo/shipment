package xyz.shipments.financial;

import xyz.shipments.financial.entity.FinancialData;
import xyz.shipments.financial.repository.FinancialDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;


@DataJpaTest
class FinancialDataRepositoryTest {

    @Autowired
    private FinancialDataRepository financialDataRepository;

    @Test
    /*
     * Test for saving financial data.
     * This test checks if the financial data is saved correctly in the repository.
     */
    void findByShipmentID_ReturnsMatchingFinancialData() {
        // Arrange
        String shipmentID = "0001";
        FinancialData data = new FinancialData();
        data.setId(UUID.randomUUID());
        data.setShipmentID(shipmentID);

        financialDataRepository.save(data);

        // Act
        List<FinancialData> result = financialDataRepository.findByShipmentID(shipmentID);

        // Assert
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getShipmentID()).isEqualTo(shipmentID);
    }
}
