package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;

import java.util.Map;

public class Payment {
    private String id;
    private String method;
    @Setter
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.status = "REJECTED";

        if ("VOUCHER".equals(method)) {
            String code = paymentData.get("voucherCode");
            if (code != null && code.length() == 16 && code.startsWith("ESHOP")) {
                long digitCount = code.chars().filter(Character::isDigit).count();
                if (digitCount == 8) {
                    this.status = "SUCCESS";
                }
            }
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            String address = paymentData.get("address");
            String fee = paymentData.get("deliveryFee");

            if (address != null && !address.trim().isEmpty() && fee != null && !fee.trim().isEmpty()) {
                this.status = "SUCCESS";
            }
        }
    }

    public String getId() { return id; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }
    public Map<String, String> getPaymentData() { return paymentData; }
    public Order getOrder() { return order; }
}