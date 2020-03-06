package com.gleb.st.debt_count.entity.debt;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class Debt {

    private int id;

    private double percent;

    private Date calculationDate;

    private int billId;

    public Debt() {}

    public Debt(int billId) {
        this.billId = billId;
    }

    public Debt(int fine, double percent, Date calculationDate, int billId) {
        this.percent = percent;
        this.calculationDate = calculationDate;
        this.billId = billId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", percent=" + percent +
                ", calculationDate=" + calculationDate +
                ", billId=" + billId +
                '}';
    }
}
