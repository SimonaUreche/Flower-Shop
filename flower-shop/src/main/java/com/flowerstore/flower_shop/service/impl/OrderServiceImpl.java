package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.dto.CheckoutRequestDTO;
import com.flowerstore.flower_shop.dto.OrderDetailsDTO;
import com.flowerstore.flower_shop.model.*;
import com.flowerstore.flower_shop.repository.OrderRepository;
import com.flowerstore.flower_shop.repository.ProductRepository;
import com.flowerstore.flower_shop.service.ICartItemService;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IProductService;
import com.flowerstore.flower_shop.service.IUserService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartItemService cartItemService;
    private final JavaMailSender mailSender;
    private final IProductService productService;
    private final IUserService userService;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ICartItemService cartItemService,
                            JavaMailSender mailSender,
                            IProductService productService,
                            IUserService userService,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.mailSender = mailSender;
        this.productService = productService;
        this.userService = userService;
        this.productRepository = productRepository;
    }


    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order with id " + id + " not found"));
    }

    @Override
    public Order updateOrder(Order order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new NoSuchElementException("Order not found for update");
        }
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Order processCheckout(CheckoutRequestDTO checkoutRequest) {
        if (checkoutRequest == null) {
            throw new IllegalArgumentException("Cererea de checkout nu poate fi nulă");
        }

        if (checkoutRequest.getOrderDetails() == null || checkoutRequest.getOrderDetails().isEmpty()) {
            throw new IllegalStateException("Coșul de cumpărături este gol");
        }

        User user;
        try {
            user = userService.getUserById(checkoutRequest.getUserId());
            if (user == null) {
                throw new IllegalStateException("Utilizatorul nu a fost găsit");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Eroare la obținerea datelor utilizatorului");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Procesata");

        List<OrderDetails> orderDetails = new ArrayList<>();
        double total = 0;

        for (OrderDetailsDTO item : checkoutRequest.getOrderDetails()) {
            try {
                Product product = productService.getProductById(item.getProductId());
                if (product == null) {
                    throw new IllegalStateException("Produsul cu ID " + item.getProductId() + " nu există");
                }

                if (product.getStock() < item.getQuantity()) {
                    throw new IllegalStateException("Stoc insuficient pentru " + product.getName() +
                            " (Disponibil: " + product.getStock() + ", Cerut: " + item.getQuantity() + ")");
                }

                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);

                OrderDetails detail = new OrderDetails();
                detail.setOrder(order);
                detail.setProduct(product);
                detail.setQuantity(item.getQuantity());
                detail.setPriceAtOrder(item.getPrice());
                detail.setSubtotal(item.getPrice() * item.getQuantity());

                orderDetails.add(detail);
                total += detail.getSubtotal();

            } catch (Exception e) {
                throw e;
            }
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(total);

        Order savedOrder;
        try {
            savedOrder = orderRepository.save(order);
        } catch (Exception e) {
            throw new IllegalStateException("Eroare la salvarea comenzii");
        }

        try {
            cartItemService.clearCart(user.getId());
        } catch (Exception e) {
            // Nu aruncăm excepție aici pentru a nu anula comanda deja plasată
        }

        sendConfirmationEmail(checkoutRequest.getEmail(), savedOrder);
        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void sendConfirmationEmail(String to, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress("simona.ureche@yahoo.com", "Flower Shop"));
            helper.setTo(to);
            helper.setSubject("Comanda #" + order.getId());
            helper.setText("Mulțumim pentru comandă!\n\nTotal: " + order.getTotalPrice() + " lei");

            mailSender.send(message);
        } catch (Exception ex) {
            System.err.println("Eroare la trimitere email: " + ex.getMessage());
            ex.printStackTrace();

        }
    }

    @Override
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found with ID: " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }


}
