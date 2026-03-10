package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentTest {

    private Order createDummyOrder() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);
        return new Order("ord-1", products, 123456789L, "Safira");
    }

    @Test
    void testPaymentDefaultStatusRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("someKey", "someValue");
        Payment payment = new Payment("1", "UNKNOWN_METHOD", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testPaymentGettersReturnCorrectValues() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ABC123");
        Order order = createDummyOrder();

        Payment payment = new Payment("pay-1", "VOUCHER", data, order);

        assertEquals("pay-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(data, payment.getPaymentData());
        assertSame(order, payment.getOrder());
    }

    @Test
    void testPaymentSetStatus() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());

        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testPaymentWithNullOrder() {
        Map<String, String> data = new HashMap<>();
        Payment payment = new Payment("1", "VOUCHER", data, null);
        assertNull(payment.getOrder());
    }
}