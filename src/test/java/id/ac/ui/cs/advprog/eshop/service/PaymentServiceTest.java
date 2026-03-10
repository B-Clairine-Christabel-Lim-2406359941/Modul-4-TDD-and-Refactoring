package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentServiceTest {

    @Test
    void testSetStatusUpdatesOrder() {
        PaymentRepository repo = new PaymentRepository();
        PaymentService service = new PaymentService(repo);

        List<Product> products = new ArrayList<>();
        Order order = new Order("ord1", products, 123456789L, "clairine.christabel", "PENDING");

        Map<String, String> paymentData = new HashMap<>();
        Payment payment = service.addPayment(order, "VOUCHER", paymentData);

        service.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", order.getStatus());

        service.setStatus(payment, "REJECTED");
        assertEquals("FAILED", order.getStatus());
    }
}