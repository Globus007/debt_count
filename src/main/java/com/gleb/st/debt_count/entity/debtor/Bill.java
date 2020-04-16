package com.gleb.st.debt_count.entity.debtor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private String number;
    private LocalDate date;
    private double amount;
    private List<Payment> payments;
    private LocalDate paymentDate;

    public Bill() {}

    public Bill(String number, LocalDate date, double amount) {
        this.number = number;
        this.date = date;
        this.amount = amount;
        payments = new ArrayList<>();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
