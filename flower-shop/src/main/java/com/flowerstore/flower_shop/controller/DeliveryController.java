package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.DeliveryDTO;
import com.flowerstore.flower_shop.dto.ProductDTO;
import com.flowerstore.flower_shop.mapper.DeliveryMapper;
import com.flowerstore.flower_shop.mapper.ProductMapper;
import com.flowerstore.flower_shop.model.Delivery;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.IDeliveryService;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/delivery")
@CrossOrigin
public class DeliveryController {

    private final IDeliveryService deliveryService;
    private final IOrderService orderService;
    private final IUserService userService;

    public DeliveryController(IDeliveryService deliveryService, IOrderService orderService, IUserService userService) {
        this.deliveryService = deliveryService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Operation(summary = "Fetch all deliveries", description = "Returns all deliveries as DTOs.")
    @ApiResponse(responseCode = "200", description = "Deliveries fetched successfully.")
    @GetMapping
    public ResponseEntity<List<DeliveryDTO>>findAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(DeliveryMapper.toDtoList(deliveries));
    }

    @Operation(summary = "Add new delivery", description = "Adds a delivery using a DTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery added."),
            @ApiResponse(responseCode = "400", description = "Invalid data.")
    })
    @PostMapping
    public ResponseEntity<DeliveryDTO> saveNewDelivery(@RequestBody DeliveryDTO dto) {
        Order order = orderService.getOrderById(dto.getOrderId());
        User deliverer = userService.getUserById(dto.getDelivererId());
        Delivery delivery = deliveryService.addDelivery(DeliveryMapper.toEntity(dto, order, deliverer));
        return ResponseEntity.ok(DeliveryMapper.toDto((delivery)));
    }

    @Operation(summary = "Get delivery by ID", description = "Returns delivery by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery found."),
            @ApiResponse(responseCode = "404", description = "Delivery not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryById(@PathVariable Long id) {
        try {
            Delivery delivery = deliveryService.getDeliveryById(id);
            return ResponseEntity.ok(DeliveryMapper.toDto(delivery));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery not found");
        }
    }

    @Operation(summary = "Update delivery", description = "Updates an existing delivery.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery updated."),
            @ApiResponse(responseCode = "400", description = "Invalid data.")
    })
    @PutMapping
    public ResponseEntity<DeliveryDTO> updateDelivery(@RequestBody DeliveryDTO dto) {
        try {
            Order order = orderService.getOrderById(dto.getOrderId());
            User deliverer = userService.getUserById(dto.getDelivererId());
            Delivery delivery = deliveryService.updateDelivery(DeliveryMapper.toEntity(dto, order, deliverer));
            return ResponseEntity.ok(DeliveryMapper.toDto(delivery));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Delete delivery", description = "Deletes a delivery by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delivery deleted."),
            @ApiResponse(responseCode = "404", description = "Delivery not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryService.getDeliveryById(id);
            deliveryService.deleteDelivery(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delivery deleted.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery not found.");
        }
    }
}
