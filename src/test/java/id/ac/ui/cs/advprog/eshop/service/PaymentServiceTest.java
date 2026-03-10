package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("ord-1", products, 123456789L, "Safira");
    }

    @Test
    void testAddPaymentVoucher() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", data);

        assertNotNull(result);
        assertEquals("VOUCHER", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCashOnDelivery() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan UI");
        data.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", data);

        assertNotNull(result);
        assertEquals("CASH_ON_DELIVERY", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentUnknownMethod() {
        Map<String, String> data = new HashMap<>();
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment result = paymentService.addPayment(order, "UNKNOWN", data);

        assertNotNull(result);
        assertEquals("UNKNOWN", result.getMethod());
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, order);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, order);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testSetStatusOther() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, order);

        Payment result = paymentService.setStatus(payment, "PENDING");

        assertEquals("PENDING", result.getStatus());
        // Order status should not change, remains its original state "WAITING_PAYMENT"
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testGetPayment() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("pay-1", "VOUCHER", data, order);
        when(paymentRepository.findById("pay-1")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-1");

        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentNotFound() {
        when(paymentRepository.findById("nonexistent")).thenReturn(null);

        Payment result = paymentService.getPayment("nonexistent");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> data = new HashMap<>();
        Payment p1 = new Payment("1", "VOUCHER", data, order);
        Payment p2 = new Payment("2", "CASH_ON_DELIVERY", data, order);
        when(paymentRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.getAllPayments();

        assertTrue(result.isEmpty());
    }
}