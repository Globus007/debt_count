package com.gleb.st.debt_count.entity.debtor;

import java.sql.Date;

public class Contract {

    private String number;
    private Date date;
    private Date paymentDate;
    private double fine;

    public Contract() {}

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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
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
