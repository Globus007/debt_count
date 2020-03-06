package com.gleb.st.debt_count.entity;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class Contract {

    private int id;
    private String info;
    private double finePercent;
    private Date calculationDate;

    public Contract() {}

    public Contract(String info, double finePercent, Date calculationDate) {
        this.info = info;
        this.finePercent = finePercent;
        this.calculationDate = calculationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getFinePercent() {
        return finePercent;
    }

    public void setFinePercent(double finePercent) {
        this.finePercent = finePercent;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", finePercent=" + finePercent +
                ", calculationDate=" + calculationDate +
                '}';
    }
}
