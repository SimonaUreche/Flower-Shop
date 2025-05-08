package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.VoucherDTO;
import com.flowerstore.flower_shop.model.Voucher;

public class VoucherMapper {
    public static VoucherDTO toDTO(Voucher voucher) {
        return new VoucherDTO(
                voucher.getId(),
                voucher.getCode(),
                voucher.getDiscount(),
                voucher.isActive()
        );
    }

    public static Voucher toEntity(VoucherDTO dto) {
        return Voucher.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .discount(dto.getDiscount())
                .isActive(dto.isActive())
                .build();
    }
}
