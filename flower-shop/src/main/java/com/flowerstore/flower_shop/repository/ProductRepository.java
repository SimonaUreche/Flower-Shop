package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Payment;
import com.flowerstore.flower_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

//@Repository
//public class ProductRepository {
//    private final List<Product> products = new ArrayList<>();
//    private Long nextId = 1L;
//
//    public Product save(Product product) {
//       if(product.getId() == null) {
//           product.setId(nextId++);
//       }
//        products.add(product);
//        return product;
//    }
//
//    public List<Product> findAll() {
//        return products;
//    }
//
//    public Product findById(Long id) {
//        return products.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public Product updateProduct(Product product) {
//        Product oldProduct = findById(product.getId());
//
//        if(oldProduct != null){
//            oldProduct.setName(product.getName());
//            oldProduct.setPrice(product.getPrice());
//            oldProduct.setStock(product.getStock());
//            oldProduct.setCategory(product.getCategory());
//            oldProduct.setImage(product.getImage());
//            oldProduct.setDescription(product.getDescription());
//            return oldProduct;
//        }
//        return null;
//    }
//
//    public boolean deleteById(Long id) {
//        return products.removeIf(product -> product.getId().equals(id));
//    }
//
//    public void saveAll(List<Product> newProducts) {
//        products.addAll(newProducts);
//    }
//}

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
}