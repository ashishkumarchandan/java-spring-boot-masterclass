package com.platform.coupling_and_ioc.repository;

import org.springframework.stereotype.Repository;

@Repository("mongoPaymentRepository")
public class MongoPaymentRepository implements PaymentRepository {
    @Override
    public void saveRecord(String transactionDetails) {
        System.out.println("[MongoPaymentRepository] Writing transaction BSON payload to MongoDB collection: " + transactionDetails);
    }

    @Override
    public String getStorageName() {
        return "MongoDB Atlas Cluster";
    }
}
