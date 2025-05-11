package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.dto.CheckoutRequestDTO;
import com.flowerstore.flower_shop.dto.OrderDetailsDTO;
import com.flowerstore.flower_shop.model.*;
import com.flowerstore.flower_shop.repository.OrderRepository;
import com.flowerstore.flower_shop.repository.ProductRepository;
import com.flowerstore.flower_shop.service.ICartItemService;
import com.flowerstore.flower_shop.service.IProductService;
import com.flowerstore.flower_shop.service.IUserService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ICartItemService cartItemService;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private IProductService productService;
    @Mock
    private IUserService userService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Product testProduct;
    private User testUser;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Trandafir")
                .price(10.0)
                .stock(20)
                .build();

        testUser = User.builder()
                .id(1L)
                .email("user@example.com")
                .name("Simona")
                .build();
    }

    @Test
    void testProcessCheckout_Success() throws Exception {
        OrderDetailsDTO detailsDTO = new OrderDetailsDTO();
        detailsDTO.setProductId(1L);
        detailsDTO.setQuantity(2);
        detailsDTO.setPrice(10.0);

        CheckoutRequestDTO checkoutRequest = new CheckoutRequestDTO();
        checkoutRequest.setUserId(1L);
        checkoutRequest.setEmail("user@example.com");
        checkoutRequest.setOrderDetails(List.of(detailsDTO));

        when(userService.getUserById(1L)).thenReturn(testUser);
        when(productService.getProductById(1L)).thenReturn(testProduct);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        Order result = orderService.processCheckout(checkoutRequest);

        assertNotNull(result);
        assertEquals(20.0, result.getTotalPrice());
        assertEquals("Procesata", result.getStatus());
        assertEquals(1, result.getOrderDetails().size());
        verify(cartItemService).clearCart(1L);
        verify(mailSender).send(any(MimeMessage.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(99L));
        assertEquals("Order with id 99 not found", ex.getMessage());
    }
}
