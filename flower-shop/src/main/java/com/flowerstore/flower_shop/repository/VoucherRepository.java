package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VoucherRepository {
    private final List<Voucher> vouchers = new ArrayList<>();
    private Long nextId = 1L;

    public Voucher save(Voucher voucher) {
        if(voucher.getId() == null) {
            voucher.setId(nextId++);
        }
        vouchers.add(voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        return vouchers;
    }

    public Voucher findById(Long id) {
        return vouchers.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public Voucher updateProduct(Voucher product) {
        Voucher oldVoucher = findById(product.getId());

        if(oldVoucher != null){
            oldVoucher.setActive(product.isActive());
            oldVoucher.setCode(product.getCode());
            oldVoucher.setDiscount(product.getDiscount());
            return oldVoucher;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return vouchers.removeIf(product -> product.getId().equals(id));
    }

}
