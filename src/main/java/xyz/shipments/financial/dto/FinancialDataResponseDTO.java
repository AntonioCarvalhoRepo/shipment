package xyz.shipments.financial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialDataResponseDTO {
    @JsonProperty("Income")
    private double income;

    @JsonProperty("Total Costs")
    private double totalCosts;

    @JsonProperty("Profit or Loss")
    private double profitOrLoss;
}
