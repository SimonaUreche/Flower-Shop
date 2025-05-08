package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.model.Voucher;
import com.flowerstore.flower_shop.repository.VoucherRepository;
import com.flowerstore.flower_shop.service.IVoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
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
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Voucher with id " + id + " not found"));
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        if (!voucherRepository.existsById(voucher.getId())) {
            throw new NoSuchElementException("Voucher not found for update");
        }
        return voucherRepository.save(voucher);
    }
    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
}
