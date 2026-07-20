package com.platform.coupling_and_ioc.service;

import com.platform.coupling_and_ioc.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // Service compiles against the contract interface, not concrete SQL or Mongo.
    private final PaymentRepository repository;

    @Autowired
    public PaymentService(@Qualifier("mongoPaymentRepository") PaymentRepository repository) {
        // Swapping this to "sqlPaymentRepository" changes data tier without rewriting service logic
        this.repository = repository;
        System.out.println("[PaymentService] Instantiated. Bound to data repository: " + repository.getStorageName());
    }

    public String processPayment(String amount) {
        String tx = "TXN_" + System.currentTimeMillis() + "_" + amount;
        repository.saveRecord(tx);
        return "Processed payment of " + amount + " stored in: " + repository.getStorageName();
    }
}
