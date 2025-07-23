package xyz.shipments.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /*
     * Test for saving financial data.
     * This test checks if the financial data is saved correctly in the repository.
     */
    @Test
    void findByShipmentID_WithPageable_ReturnsPagedResults() {
        // Arrange
        String shipmentID = "0001";
        FinancialData data = new FinancialData();
        data.setId(UUID.randomUUID());
        data.setShipmentID(shipmentID);

        financialDataRepository.save(data);

        Pageable pageable = PageRequest.of(0, 10); // First page, 10 items per page

        // Act
        Page<FinancialData> result = financialDataRepository.findByShipmentID(shipmentID, pageable);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getContent()).isNotEmpty();
        Assertions.assertThat(result.getContent().get(0).getShipmentID()).isEqualTo(shipmentID);
    }

}
