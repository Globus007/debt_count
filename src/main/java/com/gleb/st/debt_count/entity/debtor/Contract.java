package com.gleb.st.debt_count.entity.debtor;

import java.time.LocalDate;

public class Contract {

    private String number;
    private LocalDate date;
    private LocalDate paymentDate;
    private double fine;

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

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Contract{" +
                ", number=" + number +
                ", date=" + date +
                ", paymentDate=" + paymentDate +
                ", fine=" + fine +
                '}';
    }
}
