package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Voucher;

import java.util.List;

public interface IVoucherService {
    Voucher addVoucher(Voucher voucher);
    List<Voucher> getAllVouchers();
    Voucher getVoucherById(Long id);
    Voucher updateVoucher(Voucher voucher);
    void deleteVoucher(Long id);
}
