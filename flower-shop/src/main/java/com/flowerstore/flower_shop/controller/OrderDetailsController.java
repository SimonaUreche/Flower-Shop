package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.OrderDetailsDTO;
import com.flowerstore.flower_shop.mapper.OrderDetailsMapper;
import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.service.IOrderDetailsService;
import com.flowerstore.flower_shop.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {
    private final IOrderDetailsService orderDetailsService;
    private final IProductService productService;

    public OrderDetailsController(IOrderDetailsService orderDetailsService, IProductService productService) {
        this.orderDetailsService = orderDetailsService;
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<OrderDetailsDTO>> findAllOrdersDetails() {
        List<OrderDetailsDTO> dtos = orderDetailsService.getAllOrderDetails().stream()
                .map(OrderDetailsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OrderDetailsDTO> saveNewOrderDetails(@RequestBody OrderDetailsDTO dto) {
        Product product = productService.getProductById(dto.getProductId());
        OrderDetails entity = OrderDetailsMapper.toEntity(dto, product, null); // fără Order, dacă nu ai orderId în DTO
        OrderDetails saved = orderDetailsService.addOrderDetails(entity);
        return ResponseEntity.ok(OrderDetailsMapper.toDTO(saved));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long id) {
        try {
            OrderDetails details = orderDetailsService.getOrderDetailsById(id);
            return ResponseEntity.ok(OrderDetailsMapper.toDTO(details));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(@RequestBody OrderDetailsDTO dto) {
        Product product = productService.getProductById(dto.getProductId());
        OrderDetails entity = OrderDetailsMapper.toEntity(dto, product, null); // la fel, fără Order
        OrderDetails updated = orderDetailsService.updateOrderDetails(entity);
        return ResponseEntity.ok(OrderDetailsMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetails(@PathVariable Long id) {
        orderDetailsService.deleteOrderDetails(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }
}
