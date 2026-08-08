package com.agrilink.interfaces;

public interface IPaymentMethod {
    boolean processPayment(double amount);
    boolean refundPayment(double amount);
}