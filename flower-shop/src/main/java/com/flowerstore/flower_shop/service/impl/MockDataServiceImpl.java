package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.constants.UserType;
import com.flowerstore.flower_shop.model.*;
import com.flowerstore.flower_shop.repository.CategoryRepository;
import com.flowerstore.flower_shop.repository.OrderRepository;
import com.flowerstore.flower_shop.repository.ProductRepository;
import com.flowerstore.flower_shop.repository.UserRepository;
import com.flowerstore.flower_shop.repository.OrderDetailsRepository;
import com.flowerstore.flower_shop.service.MockDataService;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MockDataServiceImpl implements MockDataService {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public MockDataServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @PostConstruct
    public void init() {
        generateMockData();
    }

    @Override
    public void generateMockData() {
        List<User> users = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        //Generăm 5 utilizatori random
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setPhoneNumber(faker.phoneNumber().cellPhone());
            user.setAddress(faker.address().fullAddress());
            user.setRole(UserType.values()[random.nextInt(UserType.values().length)]);
            users.add(user);
        }

        // Generăm 5 produse random
        for (int i = 0; i < 15; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName(faker.commerce().productName());
            product.setDescription(faker.lorem().sentence());
            product.setPrice(faker.number().randomDouble(2, 5, 100));
            product.setStock(faker.number().numberBetween(10, 100));

            String[] imageUrls = {
                    "images/1.jpg",
                    "images/2.jpg",
                    "images/3.jpg",
                    "images/4.jpg",
                    "images/5.jpg",
            };

            String randomImage = imageUrls[(int) (Math.random() * imageUrls.length)];
            product.setImage(randomImage);

            CategoryRepository category = new CategoryRepository();
            category.save(new Category(1L, "Buchete"));

            products.add(product);
        }

        //listele salvate de users si products
        userRepository.saveAll(users);
        productRepository.saveAll(products);

        List<User> savedUsers = userRepository.findAll();
        List<Product> savedProducts = productRepository.findAll();

        // Generăm comenzile
        for (int i = 0; i < 5; i++) {
            Order order = new Order();

            // Selectăm un user random din lista salvată
            order.setUser(savedUsers.get(random.nextInt(savedUsers.size())));
            order.setStatus("Pending");

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            double totalPrice = 0;

            // Generăm detaliile comenzii
            int numberOfProducts = random.nextInt(3) + 1;
            for (int j = 0; j < numberOfProducts; j++) {
                OrderDetails orderDetails = new OrderDetails();
                Product product = savedProducts.get(random.nextInt(savedProducts.size()));
                int quantity = random.nextInt(5) + 1;

                orderDetails.setProduct(product);
                orderDetails.setQuantity(quantity);
                orderDetails.setPriceAtOrder(product.getPrice());
                orderDetails.setSubtotal(quantity * product.getPrice());

                totalPrice += orderDetails.getSubtotal();
                orderDetailsList.add(orderDetails);
            }

            order.setTotalPrice(totalPrice);
            order.setOrderDetails(orderDetailsList);

            //salvam order ul
            Order savedOrder = orderRepository.save(order);

            if (orderDetailsRepository != null) {
                for (OrderDetails details : orderDetailsList) {
                    details.setOrder(savedOrder);
                    OrderDetails savedDetails = orderDetailsRepository.save(details);
                }
            } else {
                System.out.println("Warning: orderDetailsRepository is null!");
            }
        }

        List<Order> savedOrders = orderRepository.findAll();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        userRepository.updateUser(updatedUser);
        return userRepository.findById(id);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        productRepository.updateProduct(updatedProduct);
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        System.out.println("Retrieved Orders: " + orders.size());
        return orders;
    }

}
