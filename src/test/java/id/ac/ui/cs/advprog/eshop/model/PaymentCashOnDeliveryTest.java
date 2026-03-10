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
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("1", "CASH_ON_DELIVERY", data, null);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryEmptyAddress() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "10000");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("2", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryEmptyFee() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan UI");
        data.put("deliveryFee", "");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("3", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryNullAddress() {
        Map<String, String> data = new HashMap<>();
        data.put("address", null);
        data.put("deliveryFee", "10000");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("4", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryNullFee() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan UI");
        data.put("deliveryFee", null);
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("5", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryBothEmpty() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("6", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryWhitespaceOnly() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "   ");
        data.put("deliveryFee", "10000");
        PaymentCashOnDelivery payment = new PaymentCashOnDelivery("7", "CASH_ON_DELIVERY", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }
}