package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentVoucherTest {
    @Test
    void testValidVoucher() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        PaymentVoucher payment = new PaymentVoucher("1", "VOUCHER", data, null);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidVoucher() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");
        PaymentVoucher payment = new PaymentVoucher("2", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }
}