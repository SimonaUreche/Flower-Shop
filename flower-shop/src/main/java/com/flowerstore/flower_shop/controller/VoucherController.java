package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.VoucherDTO;
import com.flowerstore.flower_shop.mapper.VoucherMapper;
import com.flowerstore.flower_shop.model.Voucher;
import com.flowerstore.flower_shop.service.IVoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    private final IVoucherService iVoucherService;

    public VoucherController(IVoucherService iVoucherService) {
        this.iVoucherService = iVoucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> findAllVouchers() {
        List<VoucherDTO> vouchers = iVoucherService.getAllVouchers()
                .stream()
                .map(VoucherMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vouchers);
    }

    @PostMapping
    public ResponseEntity<VoucherDTO> saveNewVoucher(@RequestBody VoucherDTO voucherDTO) {
        Voucher saved = iVoucherService.addVoucher(VoucherMapper.toEntity(voucherDTO));
        return ResponseEntity.ok(VoucherMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable Long id) {
        Voucher voucher = iVoucherService.getVoucherById(id);
        return ResponseEntity.ok(VoucherMapper.toDTO(voucher));
    }

    @PutMapping
    public ResponseEntity<VoucherDTO> updateVoucher(@RequestBody VoucherDTO voucherDTO) {
        Voucher updated = iVoucherService.updateVoucher(VoucherMapper.toEntity(voucherDTO));
        return ResponseEntity.ok(VoucherMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long id) {
        iVoucherService.deleteVoucher(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Voucher deleted.");
    }

}