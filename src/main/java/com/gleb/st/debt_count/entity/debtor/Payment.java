package com.gleb.st.debt_count.entity.debtor;

import java.sql.Date;
import java.time.LocalDate;

public class Payment {

    private LocalDate date;
    private double amount;

    public Payment() {}

    public Payment(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Payment{" +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
