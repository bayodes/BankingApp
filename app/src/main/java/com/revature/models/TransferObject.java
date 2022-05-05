package com.revature.models;

public class TransferObject {

    public int accountIdFrom;
    public int accountIdTo;
    public double amount;

    @Override
    public String toString() {
        return "TransferObject{" +
                "accountIdFrom=" + accountIdFrom +
                ", accountIdTo=" + accountIdTo +
                ", amount=" + amount +
                '}';
    }
}
