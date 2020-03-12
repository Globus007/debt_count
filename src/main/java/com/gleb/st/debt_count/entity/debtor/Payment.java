package com.gleb.st.debt_count.entity.debtor;

import java.sql.Date;

public class Payment {

    private Date date;
    private double amount;

    public Payment() {}

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Payment{" +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
