package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentVoucherTest {

    @Test
    void testValidVoucherCode() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        PaymentVoucher payment = new PaymentVoucher("1", "VOUCHER", data, null);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeTooShort() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");
        PaymentVoucher payment = new PaymentVoucher("2", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeWrongPrefix() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "XYZAB12345678901");
        PaymentVoucher payment = new PaymentVoucher("3", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeNotEnoughDigits() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOPABCDEFGHIJK");
        PaymentVoucher payment = new PaymentVoucher("4", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeNull() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", null);
        PaymentVoucher payment = new PaymentVoucher("5", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeMissingKey() {
        Map<String, String> data = new HashMap<>();
        PaymentVoucher payment = new PaymentVoucher("6", "VOUCHER", data, null);
        assertEquals("REJECTED", payment.getStatus());
    }
}