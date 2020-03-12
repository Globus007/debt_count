package com.gleb.st.debt_count.entity.debtor;

import java.sql.Date;

public class Bill {

    private String number;
    private Date date;
    private double amount;

    public Bill() {}

    public Bill(String number, Date date, double amount) {
        this.number = number;
        this.date = date;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Bill{" +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
