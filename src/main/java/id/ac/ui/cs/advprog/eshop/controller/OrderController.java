package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/create")
    public String createOrderPage() {
        return "createOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String postOrderHistory(@RequestParam String author, Model model) {
        // Menyimpan nama author dan memberikan list kosong sementara agar tidak error saat dirender
        model.addAttribute("author", author);
        model.addAttribute("orders", new ArrayList<>());
        return "orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String paymentOrderPage(@PathVariable String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "paymentOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String postPaymentOrder(@PathVariable String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "paymentOrderResult";
    }
}