package com.gleb.st.debt_count.entity.debtor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private String number;
    private Date date;
    private double amount;
    private List<Payment> payments;
    private Date paymentDate;

    public Bill() {}

    public Bill(String number, Date date, double amount) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
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
