package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentVoucher extends Payment {

    public PaymentVoucher(String id, String method, Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
        validateVoucher();
    }

    private void validateVoucher() {
        String code = this.getPaymentData().get("voucherCode");
        if (code != null && code.length() == 16 && code.startsWith("ESHOP")) {
            long digitCount = code.chars().filter(Character::isDigit).count();
            if (digitCount == 8) {
                this.setStatus("SUCCESS");
            }
        }
    }
}