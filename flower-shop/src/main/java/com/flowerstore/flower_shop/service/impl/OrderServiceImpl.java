package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.dto.CheckoutRequestDTO;
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
        Order order = orderRepository.findById(id);
        if(order != null){
            return order;
        }
        throw new NoSuchElementException("Order with id " + id + " not found");
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.updateOrderStatus(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional//toate op. din metoda asta sunt tratat ca o singura tranzatie in baza de date
    public Order processCheckout(CheckoutRequestDTO checkoutRequest) {

        if (checkoutRequest == null || checkoutRequest.getOrderDetails() == null) {
            throw new IllegalStateException("Datele comenzii sunt invalide");
        }

        if (checkoutRequest.getOrderDetails().isEmpty()) {
            throw new IllegalStateException("Coșul este gol");
        }

        // Validare user
//        User user = userService.getUserById(checkoutRequest.getUserId());
//        if (user == null) {
//            throw new IllegalStateException("Utilizatorul nu există");
//        }

        User user = null; //~guest
        try {
            user = userService.getUserById(checkoutRequest.getUserId());
        } catch (Exception ignored) {}

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Procesată");

        List<OrderDetails> orderDetails = checkoutRequest.getOrderDetails().stream()
                .map(item -> {
                    //Validare produs
                    Product product = productService.getProductById(item.getProductId());

                    //Validare stoc
                    if (product.getStock() < item.getQuantity()) {
                        throw new IllegalStateException("Stoc insuficient pentru produsul: " + product.getName());
                    }

                    //Actualizare stoc
                    product.setStock(product.getStock() - item.getQuantity());
                    productRepository.save(product);

                    //detali comandă
                    return new OrderDetails(
                            null,
                            order,
                            product,
                            item.getQuantity(),
                            item.getPrice(),
                            item.getPrice() * item.getQuantity()
                    );
                })
                .collect(Collectors.toList());

        double total = orderDetails.stream()
                .mapToDouble(OrderDetails::getSubtotal)
                .sum();

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);
        cartItemService.clearCart(checkoutRequest.getUserId());

        sendConfirmationEmail(checkoutRequest.getEmail(), savedOrder);
        return savedOrder;
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

}
