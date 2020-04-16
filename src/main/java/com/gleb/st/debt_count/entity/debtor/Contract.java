package com.gleb.st.debt_count.entity.debtor;

import java.time.LocalDate;

public class Contract {

    private String number;
    private LocalDate date;
    private LocalDate paymentDate;

    public Contract() {}

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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                ", number=" + number +
                ", date=" + date +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
