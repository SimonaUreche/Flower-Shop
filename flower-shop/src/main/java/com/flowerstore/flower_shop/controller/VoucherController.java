package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Voucher;
import com.flowerstore.flower_shop.service.IVoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    private final IVoucherService iVoucherService;

    public VoucherController(IVoucherService iVoucherService) {
        this.iVoucherService = iVoucherService;
    }

    @GetMapping
    public ResponseEntity findAllVouchers() {
        return ResponseEntity.status(HttpStatus.OK).body(iVoucherService.getAllVouchers());
    }

    @PostMapping
    public ResponseEntity saveNewVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.status(HttpStatus.OK).body(iVoucherService.addVoucher(voucher));
    }

    @GetMapping("/{id}")
    public ResponseEntity getVoucher(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iVoucherService.getVoucherById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.status(HttpStatus.OK).body(iVoucherService.updateVoucher(voucher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVoucher(@PathVariable Long id) {
        iVoucherService.deleteVoucher(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }

}