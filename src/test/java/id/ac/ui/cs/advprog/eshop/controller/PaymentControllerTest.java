package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    void testGetPaymentDetailForm() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentDetailById() throws Exception {
        mockMvc.perform(get("/payment/detail/pay-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminList() throws Exception {
        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminDetail() throws Exception {
        mockMvc.perform(get("/payment/admin/detail/pay-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentAdminSetStatus() throws Exception {
        // Create prerequisite data
        Product product = new Product();
        product.setProductName("Admin Test Product");
        product.setProductQuantity(1);
        productService.create(product);

        List<Product> products = productService.findAll();
        Order order = new Order("admin-test-order", products, System.currentTimeMillis(), "admin");
        orderService.createOrder(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        mockMvc.perform(post("/payment/admin/set-status/" + payment.getId())
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection());
    }
}