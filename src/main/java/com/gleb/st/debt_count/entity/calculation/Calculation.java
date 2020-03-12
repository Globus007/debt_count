package com.gleb.st.debt_count.entity.calculation;

public class Calculation {

    private double debt;
    private double fine;
    private double percent;
    private double fullDebt;

    public Calculation() {}

    public Calculation(double debt, double fine, double percent) {
        this.debt = debt;
        this.fine = fine;
        this.percent = percent;
        fullDebt = debt + fine + percent;
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

    @Override
    public String toString() {
        return "Calculation{" +
                ", debt=" + debt +
                ", fine=" + fine +
                ", percent=" + percent +
                ", fullDebt=" + fullDebt +
                '}';
    }
}
