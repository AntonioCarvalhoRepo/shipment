package dachser.shipments.shipments.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CostRequestDTO {
    @JsonProperty("cost")
    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", message = "The value must be positive with one decimal house")
    private double cost;

    @JsonProperty("additionalCost")
    @DecimalMin(value = "0.0", message = "The value must be positive with one decimal house")
    private double additionalCost;
}
