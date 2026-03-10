package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> paymentList = new ArrayList<>();

    public Payment save(Payment payment) {
        paymentList.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment p : paymentList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return paymentList;
    }
}