package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author, Model model) {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            model.addAttribute("error", "No products available to create an order. Please create a product first.");
            return "createOrder";
        }

        String orderId = UUID.randomUUID().toString();
        Long orderTime = System.currentTimeMillis();
        Order order = new Order(orderId, products, orderTime, author);
        orderService.createOrder(order);

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String postOrderHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("author", author);
        model.addAttribute("orders", orders);
        return "orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String paymentOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("order", order);
        return "paymentOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String postPaymentOrder(@PathVariable String orderId,
                                   @RequestParam String paymentMethod,
                                   @RequestParam(required = false) String voucherCode,
                                   @RequestParam(required = false) String address,
                                   @RequestParam(required = false) String deliveryFee,
                                   Model model) {
        Order order = orderService.findById(orderId);

        Map<String, String> paymentData = new HashMap<>();
        if ("VOUCHER".equals(paymentMethod)) {
            paymentData.put("voucherCode", voucherCode != null ? voucherCode : "");
        } else if ("CASH_ON_DELIVERY".equals(paymentMethod)) {
            paymentData.put("address", address != null ? address : "");
            paymentData.put("deliveryFee", deliveryFee != null ? deliveryFee : "");
        }

        Payment payment = paymentService.addPayment(order, paymentMethod, paymentData);

        model.addAttribute("orderId", orderId);
        model.addAttribute("payment", payment);
        return "paymentOrderResult";
    }
}