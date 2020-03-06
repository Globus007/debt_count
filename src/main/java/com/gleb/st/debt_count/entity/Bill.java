package com.gleb.st.debt_count.entity;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class Bill {

    private int id;

    private String number;

    private Date date;

    private double amount;

    private Date payDate;

    private int contractId;

    public Bill() {}

    public Bill(String number, Date date, double amount, Date payDate) {
        this.number = number;
        this.date = date;
        this.amount = amount;
        this.payDate = payDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", payDate=" + payDate +
                ", contractId=" + contractId +
                '}';
    }
}
