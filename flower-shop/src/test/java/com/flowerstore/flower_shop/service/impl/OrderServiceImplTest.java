//package com.flowerstore.flower_shop.service.impl;
//
//import com.flowerstore.flower_shop.model.Order;
//import com.flowerstore.flower_shop.model.User;
//import com.flowerstore.flower_shop.model.OrderDetails;
//import com.flowerstore.flower_shop.repository.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class OrderServiceImplTest {
//
//    private OrderServiceImpl orderService;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    private Order testOrder;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        orderService = new OrderServiceImpl(orderRepository);
//        testOrder = new Order(1L, new User(), List.of(new OrderDetails()), 100.0, "Pending");
//    }
//
//    @Test
//    void givenValidOrder_whenAddOrder_thenReturnSavedOrder() {
//        when(orderRepository.save(testOrder)).thenReturn(testOrder);
//
//        Order result = orderService.addOrder(testOrder);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals(100.0, result.getTotalPrice());
//        verify(orderRepository, times(1)).save(testOrder);
//    }
//
//    @Test
//    void whenGetAllOrders_thenReturnOrderList() {
//        List<Order> orders = List.of(testOrder);
//        when(orderRepository.findAll()).thenReturn(orders);
//
//        List<Order> result = orderService.getAllOrders();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(orderRepository, times(1)).findAll();
//    }
//
//    @Test
//    void givenExistingOrder_whenGetOrderById_thenReturnOrder() {
//        when(orderRepository.findById(1L)).thenReturn(testOrder);
//
//        Order result = orderService.getOrderById(1L);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Pending", result.getStatus());
//        verify(orderRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void givenNonExistingOrder_whenGetOrderById_thenThrowException() {
//        when(orderRepository.findById(1L)).thenReturn(null);
//
//        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(1L));
//    }
//
//    @Test
//    void givenValidOrder_whenUpdateOrder_thenReturnUpdatedOrder() {
//        Order updatedOrder = new Order(1L, new User(), List.of(new OrderDetails()), 120.0, "Completed");
//        when(orderRepository.updateOrderStatus(updatedOrder)).thenReturn(updatedOrder);
//
//        Order result = orderService.updateOrder(updatedOrder);
//
//        assertNotNull(result);
//        assertEquals("Completed", result.getStatus());
//        assertEquals(120.0, result.getTotalPrice());
//        verify(orderRepository, times(1)).updateOrderStatus(updatedOrder);
//    }
//
//    @Test
//    void givenOrderId_whenDeleteOrder_thenVerifyDeletion() {
//        doNothing().when(orderRepository).deleteById(1L);
//
//        orderService.deleteOrder(1L);
//
//        verify(orderRepository, times(1)).deleteById(1L);
//    }
//}