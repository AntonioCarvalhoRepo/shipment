package dachser.shipments.shipments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentRequestDTO {
    @JsonProperty("shipmentID")
    @NotNull(message = "Shipment ID is required")
    private String shipmentID;

    @JsonProperty("income")
    @NotNull(message = "Income is required")
    private IncomeRequestDTO income;

    @JsonProperty("costs")
    @NotNull(message = "Costs is required")
    private CostRequestDTO costs;
}
