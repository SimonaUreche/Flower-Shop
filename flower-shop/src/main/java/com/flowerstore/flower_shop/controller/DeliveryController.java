package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Delivery;
import com.flowerstore.flower_shop.service.IDeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private final IDeliveryService iDeliveryService;

    public DeliveryController(IDeliveryService iDeliveryService) {
        this.iDeliveryService = iDeliveryService;
    }

    @Operation(summary = "Fetch all deliveries", description = "This method retrieves all delivery records from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully returned all deliveries.")
    @GetMapping
    public ResponseEntity findAllDeliveries() {
        return ResponseEntity.status(HttpStatus.OK).body(iDeliveryService.getAllDeliveries());
    }

    @Operation(summary = "Add a new delivery", description = "This method adds a new delivery record to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the delivery."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PostMapping
    public ResponseEntity saveNewDeliveries(@RequestBody Delivery delivery) {
        return ResponseEntity.status(HttpStatus.OK).body(iDeliveryService.addDelivery(delivery));
    }

    @Operation(summary = "Fetch delivery by ID", description = "This method retrieves a delivery record by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the delivery."),
            @ApiResponse(responseCode = "404", description = "Delivery not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity getDeliveries(@Parameter(description = "ID of the delivery to retrieve", required = true)
                                        @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iDeliveryService.getDeliveryById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Update an existing delivery", description = "This method updates the details of an existing delivery record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the delivery."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PutMapping
    public ResponseEntity updateDeliveries(@RequestBody Delivery delivery) {
        return ResponseEntity.status(HttpStatus.OK).body(iDeliveryService.updateDelivery(delivery));
    }

    @Operation(summary = "Delete a delivery by ID", description = "This method deletes a delivery record based on the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the delivery."),
            @ApiResponse(responseCode = "404", description = "Delivery not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeliveries(@Parameter(description = "ID of the delivery to delete", required = true)
                                           @PathVariable Long id) {
        iDeliveryService.deleteDelivery(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successful operation");
    }
}
