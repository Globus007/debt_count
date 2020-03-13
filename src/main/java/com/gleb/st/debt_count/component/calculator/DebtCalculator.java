package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.component.expiration.counter.ExpirationCounter;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import com.gleb.st.debt_count.entity.calculation.RefinancingRate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Calendar;

public abstract class DebtCalculator {

    @Autowired
    protected RefinancingRateReader refinancingRateReader;
    @Autowired
    protected ExpirationCounter expirationCounter;

    protected CalculationData calculationData;

    public DebtCalculator(CalculationData calculationData) {
        this.calculationData = calculationData;
    }

    public DebtCalculator setCalculationData(CalculationData calculationData) {
        this.calculationData = calculationData;
        return this;
    }

    public abstract Calculation processCalculation();

    protected double calculateFine(double amount, double percent, long delayPeriod) {
        // Пеня = долг * ставка * дни просрочки
        return amount * (percent / 100) * delayPeriod;
    }

    protected double calculatePercent(double amount, Date calculationDate, long delayPeriod) {
        // ставка рефинансирования на дату рассчета
        RefinancingRate rate = refinancingRateReader.getRefinancingRareOnDate(calculationDate);
        // Проценты = долг * ставка нбрб/100 * дни/365
        return amount * (rate.getValue() / 100) * ((double) delayPeriod / 365);
    }

    protected Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }
}
