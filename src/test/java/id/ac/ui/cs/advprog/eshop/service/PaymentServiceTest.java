package id.ac.ui.cs.advprog.eshop.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

class PaymentServiceTest {
    @Test
    void testSetStatusUpdatesOrder() {
        PaymentRepository repo = new PaymentRepository();
        PaymentService service = new PaymentService(repo);

        Order order = new Order("ord1", "PENDING");
        Payment payment = service.addPayment(order, "VOUCHER", new HashMap<>());

        service.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", order.getStatus());

        service.setStatus(payment, "REJECTED");
        assertEquals("FAILED", order.getStatus());
    }
}