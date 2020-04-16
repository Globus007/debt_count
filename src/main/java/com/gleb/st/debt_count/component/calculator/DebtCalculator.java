package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.component.expiration.counter.ExpirationCounter;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.calculation.RefinancingRate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class DebtCalculator {

    @Autowired
    protected RefinancingRateReader refinancingRateReader;
    @Autowired
    protected ExpirationCounter expirationCounter;

    protected CalculationInputData inputData;

    public DebtCalculator(CalculationInputData inputData) {
        this.inputData = inputData;
    }

    public DebtCalculator setInputData(CalculationInputData inputData) {
        this.inputData = inputData;
        return this;
    }

    public abstract Calculation processCalculation();

    protected double calculateFine(double amount, double percent, long delayPeriod) {
        // Пеня = долг * ставка * дни просрочки
        return amount * (percent / 100) * delayPeriod;
    }

    protected double calculatePercent(double amount, LocalDate calculationDate, long delayPeriod) {
        // ставка рефинансирования на дату рассчета
        RefinancingRate rate = refinancingRateReader.getRefinancingRateOnDate(calculationDate);
        // Проценты = долг * ставка нбрб/100 * дни/365
        return amount * (rate.getValue() / 100) * ((double) delayPeriod / 365);
    }

    protected double calculatePercent(double amount, double refinancingRate, long delayPeriod) {
        // Проценты = долг * ставка нбрб/100 * дни/365
        return amount * (refinancingRate / 100) * ((double) delayPeriod / 365);
    }

}
