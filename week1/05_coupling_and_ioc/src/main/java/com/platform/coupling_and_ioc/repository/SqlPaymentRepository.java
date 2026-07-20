package com.platform.coupling_and_ioc.repository;

import org.springframework.stereotype.Repository;

@Repository("sqlPaymentRepository")
public class SqlPaymentRepository implements PaymentRepository {
    @Override
    public void saveRecord(String transactionDetails) {
        System.out.println("[SqlPaymentRepository] Writing transaction block to relational database: " + transactionDetails);
    }

    @Override
    public String getStorageName() {
        return "Microsoft SQL Server Instance";
    }
}
