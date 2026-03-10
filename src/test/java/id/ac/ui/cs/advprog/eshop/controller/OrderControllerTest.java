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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private PaymentService paymentService;

    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("ord-123", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testGetCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"));
    }

    @Test
    void testPostCreateOrderSuccess() throws Exception {
        when(productService.findAll()).thenReturn(products);
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/order/create")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testPostCreateOrderNoProducts() throws Exception {
        when(productService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/order/create")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("error"));

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    void testGetOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistory"));
    }

    @Test
    void testPostOrderHistory() throws Exception {
        List<Order> orders = List.of(order);
        when(orderService.findAllByAuthor("Safira Sudrajat")).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistory"))
                .andExpect(model().attribute("author", "Safira Sudrajat"))
                .andExpect(model().attribute("orders", orders));
    }

    @Test
    void testGetPaymentOrderPage() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);

        mockMvc.perform(get("/order/pay/ord-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentOrder"))
                .andExpect(model().attribute("orderId", "ord-123"))
                .andExpect(model().attribute("order", order));
    }

    @Test
    void testPostPaymentOrderVoucher() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);
        Payment payment = new Payment("pay-1", "VOUCHER",
                Map.of("voucherCode", "ESHOP1234ABC5678"), order);
        when(paymentService.addPayment(eq(order), eq("VOUCHER"), anyMap()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/ord-123")
                        .param("paymentMethod", "VOUCHER")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentOrderResult"))
                .andExpect(model().attribute("orderId", "ord-123"))
                .andExpect(model().attribute("payment", payment));
    }

    @Test
    void testPostPaymentOrderCashOnDelivery() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);
        Payment payment = new Payment("pay-2", "CASH_ON_DELIVERY",
                Map.of("address", "Jalan UI", "deliveryFee", "10000"), order);
        when(paymentService.addPayment(eq(order), eq("CASH_ON_DELIVERY"), anyMap()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/ord-123")
                        .param("paymentMethod", "CASH_ON_DELIVERY")
                        .param("address", "Jalan UI")
                        .param("deliveryFee", "10000"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentOrderResult"))
                .andExpect(model().attribute("payment", payment));
    }

    @Test
    void testPostPaymentOrderVoucherNullCode() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);
        Payment payment = new Payment("pay-3", "VOUCHER",
                Map.of("voucherCode", ""), order);
        when(paymentService.addPayment(eq(order), eq("VOUCHER"), anyMap()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/ord-123")
                        .param("paymentMethod", "VOUCHER")) // No voucherCode param
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentOrderCashOnDeliveryNullParams() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);
        Payment payment = new Payment("pay-4", "CASH_ON_DELIVERY",
                Map.of("address", "", "deliveryFee", ""), order);
        when(paymentService.addPayment(eq(order), eq("CASH_ON_DELIVERY"), anyMap()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/ord-123")
                        .param("paymentMethod", "CASH_ON_DELIVERY")) // No address/deliveryFee param
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentOrderUnknownMethod() throws Exception {
        when(orderService.findById("ord-123")).thenReturn(order);
        Payment payment = new Payment("pay-5", "UNKNOWN_METHOD",
                Map.of(), order); // Empty map for unknown method
        when(paymentService.addPayment(eq(order), eq("UNKNOWN_METHOD"), anyMap()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/ord-123")
                        .param("paymentMethod", "UNKNOWN_METHOD"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentOrderResult"))
                .andExpect(model().attribute("payment", payment));
    }
}