package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final IOrderService iorderService;
    private final IUserService iuserService;

    public OrderController(IOrderService iorderService, IUserService iuserService) {
        this.iorderService = iorderService;
        this.iuserService = iuserService;
    }

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = iorderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list-orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Order order = iorderService.getOrderById(id);
            if (order == null) {
                return "redirect:/order";
            }
            model.addAttribute("order", order);
            return "orders/edit-order";
        } catch (NoSuchElementException e) {
            return "redirect:/order";
        }
    }

    @PostMapping("/update")
    public String updateOrder(@RequestParam("id") Long id, @RequestParam("status") String status) {
        try {
            Order existingOrder = iorderService.getOrderById(id);
            if (existingOrder != null) {
                existingOrder.setStatus(status);
                iorderService.updateOrder(existingOrder);
            }
            return "redirect:/order";
        } catch (Exception e) {
            return "redirect:/order";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        iorderService.deleteOrder(id);
        return "redirect:/order";
    }

    @PostMapping("/save")
    public String saveOrders(@ModelAttribute Order order) {
        iorderService.addOrder(order);
        return "redirect:/order";
    }
}
