package com.gleb.st.debt_count.entity.calculation;

import java.util.List;

public class Calculation {

    private double debt;
    private double fine;
    private double percent;
    private double fullDebt;
    private List<String> calculatingInfo;

    public Calculation() {}

    public Calculation(double debt, double fine, double percent, List<String> calculatingInfo) {
        this.debt = debt;
        this.fine = fine;
        this.percent = percent;
        this.calculatingInfo = calculatingInfo;
        fullDebt = debt + fine + percent;
    }

    public List<String> getCalculatingInfo() {
        return calculatingInfo;
    }

    public void setCalculatingInfo(List<String> calculatingInfo) {
        this.calculatingInfo = calculatingInfo;
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
                "debt=" + debt +
                ", fine=" + fine +
                ", percent=" + percent +
                ", fullDebt=" + fullDebt +
                ", calculatingInfo=" + calculatingInfo +
                '}';
    }
}
