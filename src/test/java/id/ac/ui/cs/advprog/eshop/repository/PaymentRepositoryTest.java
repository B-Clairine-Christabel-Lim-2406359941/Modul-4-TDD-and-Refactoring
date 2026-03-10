package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentRepositoryTest {

    private PaymentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new PaymentRepository();
    }

    @Test
    void testSaveAndFindById() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, null);
        repo.save(payment);

        Payment found = repo.findById("1");
        assertEquals(payment, found);
        assertEquals("1", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment result = repo.findById("nonexistent");
        assertNull(result);
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> result = repo.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllWithMultiplePayments() {
        Map<String, String> data = new HashMap<>();
        Payment payment1 = new Payment("1", "VOUCHER", data, null);
        Payment payment2 = new Payment("2", "CASH_ON_DELIVERY", data, null);

        repo.save(payment1);
        repo.save(payment2);

        List<Payment> result = repo.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testSaveMultipleAndFindCorrectOne() {
        Map<String, String> data = new HashMap<>();
        Payment payment1 = new Payment("1", "VOUCHER", data, null);
        Payment payment2 = new Payment("2", "CASH_ON_DELIVERY", data, null);

        repo.save(payment1);
        repo.save(payment2);

        assertEquals(payment1, repo.findById("1"));
        assertEquals(payment2, repo.findById("2"));
    }
}