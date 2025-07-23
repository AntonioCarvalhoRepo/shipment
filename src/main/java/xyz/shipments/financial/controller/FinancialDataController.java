package xyz.shipments.financial.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import xyz.shipments.financial.dto.ShipmentRequestDTO;
import xyz.shipments.financial.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/financialData", produces = MediaType.APPLICATION_JSON_VALUE)
public class FinancialDataController {
    private final ShipmentService userService;

    public FinancialDataController(ShipmentService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Calculate profit or loss for a shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Calculated financial data"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content) })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createFinancialData(@Valid @RequestBody ShipmentRequestDTO shipmentData){
        userService.createFinancialData(shipmentData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all financial data for a shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Shipment financial data retrieved", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Shipment not found",
                    content = @Content) })
    @GetMapping(path = "/{shipmentId}")
    public ResponseEntity<?> getFinancialDataByShipmentId(@PathVariable String shipmentId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        log.debug("Fetching financial data for shipment ID: {}", shipmentId);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFinancialDataByShipmentId(shipmentId,pageable));
    }
}
