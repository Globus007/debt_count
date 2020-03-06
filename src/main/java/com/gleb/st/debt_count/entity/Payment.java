package com.gleb.st.debt_count.entity;

import java.sql.Date;

public class Payment {

    private int id;
    private Date date;
    private double amount;
    private int contractId;

    public Payment() {}

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Payment(int contractId) {
        this.contractId = contractId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", contractId=" + contractId +
                '}';
    }
}
