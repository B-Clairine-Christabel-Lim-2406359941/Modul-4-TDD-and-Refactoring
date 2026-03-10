package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentRepositoryTest {
    @Test
    void testSaveAndFindPayment() {
        PaymentRepository repo = new PaymentRepository();
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", paymentData, null);
        repo.save(payment);

        assertEquals(payment, repo.findById("1"));
        assertFalse(repo.findAll().isEmpty());
    }
}