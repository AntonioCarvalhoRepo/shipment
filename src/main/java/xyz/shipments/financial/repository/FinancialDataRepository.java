package xyz.shipments.financial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.shipments.financial.entity.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FinancialDataRepository extends JpaRepository<FinancialData, UUID> {
    Page<FinancialData> findByShipmentID(String shipmentID, Pageable pageable);
}
