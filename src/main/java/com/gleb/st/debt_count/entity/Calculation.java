package com.gleb.st.debt_count.entity;

import org.springframework.stereotype.Component;

@Component
public class Calculation {

    private int id;
    private double debt;
    private double fine;
    private double percent;
    private double fullDebt;
    private int contractId;

    public Calculation() {}

    public Calculation(double debt, double fine, double percent) {
        this.debt = debt;
        this.fine = fine;
        this.percent = percent;
        fullDebt = debt + fine + percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getFullDebt() {
        return fullDebt;
    }

    public void setFullDebt(double fullDebt) {
        this.fullDebt = fullDebt;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "id=" + id +
                ", debt=" + debt +
                ", fine=" + fine +
                ", percent=" + percent +
                ", fullDebt=" + fullDebt +
                ", contractId=" + contractId +
                '}';
    }
}
