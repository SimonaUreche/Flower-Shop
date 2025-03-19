package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Voucher;
import com.flowerstore.flower_shop.repository.VoucherRepository;
import com.flowerstore.flower_shop.service.IVoucherService;

import java.util.List;
import java.util.NoSuchElementException;

public class VoucherServiceImpl implements IVoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    @Override
    public Voucher addVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id);
        if(voucher != null){
            return voucher;
        }
        throw new NoSuchElementException("Voucher with id " + id + " not found");
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.updateProduct(voucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
}
