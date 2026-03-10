package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    @Test
    void testPaymentContainsCorrectDataAndDefaultStatusRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("someKey", "someValue");

        Payment payment = new Payment("1", "UNKNOWN_METHOD", data, null);

        assertEquals("1", payment.getId());
        assertEquals("UNKNOWN_METHOD", payment.getMethod());
        assertEquals(data, payment.getPaymentData());
        assertNull(payment.getOrder());
        assertEquals("REJECTED", payment.getStatus());
    }
}