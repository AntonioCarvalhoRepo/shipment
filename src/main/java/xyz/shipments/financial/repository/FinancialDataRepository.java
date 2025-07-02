package xyz.shipments.financial.repository;

import xyz.shipments.financial.entity.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FinancialDataRepository extends JpaRepository<FinancialData, UUID> {
    List<FinancialData> findByShipmentID(String shipmentID);
}
