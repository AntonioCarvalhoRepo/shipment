package xyz.shipments.financial.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "financial_data",indexes = {
        @Index(name = "idx_shipmentID", columnList = "shipmentID")
}
)
public class FinancialData {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name="id", unique = true, nullable = false)
    public UUID id;

    @Column(name = "shipmentID")
    private String shipmentID;

    @Column(name = "income")
    private Double income;

    @Column(name = "costs")
    private Double costs;

    @Column(name = "balance")
    private Double balance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created")
    @CreationTimestamp
    public Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified")
    @UpdateTimestamp
    public Date modified;
}
