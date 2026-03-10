package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    void testGetCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostCreateOrder() throws Exception {
        // Create a product first so order creation succeeds
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(5);
        productService.create(product);

        mockMvc.perform(post("/order/create")
                        .param("author", "testAuthor"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testGetOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostOrderHistory() throws Exception {
        mockMvc.perform(post("/order/history")
                        .param("author", "clairine.christabel"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentOrderPage() throws Exception {
        mockMvc.perform(get("/order/pay/ord-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentOrder() throws Exception {
        // Create a product and order first
        Product product = new Product();
        product.setProductName("Payment Test Product");
        product.setProductQuantity(1);
        productService.create(product);

        // Create order via service
        java.util.List<Product> products = productService.findAll();
        Order order = new Order("test-order-pay", products, System.currentTimeMillis(), "testUser");
        orderService.createOrder(order);

        mockMvc.perform(post("/order/pay/test-order-pay")
                        .param("paymentMethod", "VOUCHER")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk());
    }
}