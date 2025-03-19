package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MockDataService {
    void generateMockData();

    List<User> getUsers();
    ///////////////REACT
    User addUser(User user);
    void deleteUser(Long id);
    User updateUser(Long id, User updatedUser);
    ///////////////REACT

    List<Product> getProducts();
    ///////////////REACT
    Product addProduct(Product product);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
    ///////////////REACT

    List<Order> getOrders();
}
