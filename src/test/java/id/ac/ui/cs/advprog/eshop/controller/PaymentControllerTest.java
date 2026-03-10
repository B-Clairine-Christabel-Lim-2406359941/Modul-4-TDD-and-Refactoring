package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment payment;
    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("ord-123", products, 1708560000L, "Safira Sudrajat");
        payment = new Payment("pay-123", "VOUCHER",
                Map.of("voucherCode", "ESHOP1234ABC5678"), order);
    }

    @Test
    void testGetPaymentDetailForm() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetailForm"));
    }

    @Test
    void testGetPaymentDetailByIdFound() throws Exception {
        when(paymentService.getPayment("pay-123")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/pay-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"))
                .andExpect(model().attribute("payment", payment))
                .andExpect(model().attribute("paymentId", "pay-123"));
    }

    @Test
    void testGetPaymentDetailByIdNotFound() throws Exception {
        when(paymentService.getPayment("pay-999")).thenReturn(null);

        mockMvc.perform(get("/payment/detail/pay-999"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"))
                .andExpect(model().attributeDoesNotExist("payment"));
    }

    @Test
    void testGetPaymentAdminList() throws Exception {
        List<Payment> payments = List.of(payment);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentList"))
                .andExpect(model().attribute("payments", payments));
    }

    @Test
    void testGetPaymentAdminDetail() throws Exception {
        when(paymentService.getPayment("pay-123")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/pay-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"))
                .andExpect(model().attribute("payment", payment));
    }

    @Test
    void testPostSetPaymentStatusSuccess() throws Exception {
        when(paymentService.getPayment("pay-123")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/pay-123")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));

        verify(paymentService, times(1)).setStatus(payment, "SUCCESS");
    }

    @Test
    void testPostSetPaymentStatusNotFound() throws Exception {
        when(paymentService.getPayment("pay-999")).thenReturn(null);

        mockMvc.perform(post("/payment/admin/set-status/pay-999")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));

        verify(paymentService, never()).setStatus(any(), anyString());
    }
}