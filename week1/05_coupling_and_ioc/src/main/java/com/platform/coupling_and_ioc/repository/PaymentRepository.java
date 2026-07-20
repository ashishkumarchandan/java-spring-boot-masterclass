package com.platform.coupling_and_ioc.repository;

public interface PaymentRepository {
    void saveRecord(String transactionDetails);
    String getStorageName();
}
