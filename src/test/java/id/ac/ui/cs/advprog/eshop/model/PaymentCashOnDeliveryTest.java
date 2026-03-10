package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentCashOnDeliveryTest {
    @Test
    void testValidCashOnDelivery() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan UI");
        data.put("deliveryFee", "10000");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("3", "CASH_ON_DELIVERY", data, null);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDelivery() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "10000");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("4", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }
}