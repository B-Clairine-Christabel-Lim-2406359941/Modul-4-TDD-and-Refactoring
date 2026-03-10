package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentCashOnDelivery extends Payment {

    public PaymentCashOnDelivery(String id, String method, Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
        validateCashOnDelivery();
    }

    private void validateCashOnDelivery() {
        String address = this.getPaymentData().get("address");
        String fee = this.getPaymentData().get("deliveryFee");

        boolean isAddressValid = address != null && !address.trim().isEmpty();
        boolean isFeeValid = fee != null && !fee.trim().isEmpty();

        if (isAddressValid && isFeeValid) {
            this.setStatus("SUCCESS");
        }
    }
}