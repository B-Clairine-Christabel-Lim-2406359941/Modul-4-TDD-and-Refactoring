package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    @Test
    void testValidVoucherCode() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("1", "VOUCHER", data);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCode() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");
        Payment payment = new Payment("2", "VOUCHER", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidCashOnDelivery() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan UI");
        data.put("deliveryFee", "10000");
        Payment payment = new Payment("3", "CASH_ON_DELIVERY", data);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDelivery() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "10000");
        Payment payment = new Payment("4", "CASH_ON_DELIVERY", data);
        assertEquals("REJECTED", payment.getStatus());
    }
}