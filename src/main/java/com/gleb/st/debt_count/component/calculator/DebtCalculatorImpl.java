package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.component.expiration.Expiration;
import com.gleb.st.debt_count.component.expiration.counter.ExpirationCounter;
import com.gleb.st.debt_count.component.refinancing.rate.RefinancingRate;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
import com.gleb.st.debt_count.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Component
public class DebtCalculatorImpl implements DebtCalculator {

    private final RefinancingRateReader refinancingRateReader;
    private final ExpirationCounter expirationCounter;

    @Autowired
    public DebtCalculatorImpl(RefinancingRateReader refinancingRateReader, ExpirationCounter expirationCounter) {
        this.refinancingRateReader = refinancingRateReader;
        this.expirationCounter = expirationCounter;
    }

    @Override
    public Calculation processCalculation(Contract contract, Bill bill) {

        Expiration expiration = expirationCounter.calculateExpiration(bill.getPayDate(), contract.getCalculationDate());

        double fine = calculateFine(bill.getAmount(), contract.getFinePercent(), expiration.getValue());
        double percent = calculatePercent(bill.getAmount(), contract.getCalculationDate(), expiration.getValue());

        return new Calculation(bill.getAmount(), fine, percent);
    }

    @Override
    public Calculation processCalculation(Contract contract, Bill bill, List<Payment> payments) {

        double debt = bill.getAmount();
        double fine = 0, percent = 0;
        Date startCountDate = bill.getPayDate();
        Expiration expiration;

        for (Payment payment: payments) {
            expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
            fine += calculateFine(debt, contract.getFinePercent(), expiration.getValue());

            expiration = expirationCounter.calculateExpiration(bill.getPayDate(), payment.getDate());
            percent += calculatePercent(payment.getAmount(), payment.getDate(), expiration.getValue());

            debt -= payment.getAmount();
            startCountDate = getNextDay(payment.getDate());
        }

        expiration = expirationCounter.calculateExpiration(startCountDate, contract.getCalculationDate());
        fine += calculateFine(debt, contract.getFinePercent(), expiration.getValue());

        expiration = expirationCounter.calculateExpiration(bill.getPayDate(), contract.getCalculationDate());
        percent += calculatePercent(debt, contract.getCalculationDate(), expiration.getValue());

        // todo: Ставка пени может быть ограничена. Например ставка0.15% но не более 10% от долга
        return new Calculation(debt, fine, percent);
    }

    @Override
    public Calculation processCalculation(Contract contract, List<Bill> bills, List<Payment> payments) {
        return new Calculation();
    }

    private double calculateFine(double amount, double percent, long delayPeriod) {
        // Пеня = долг * ставка * дни просрочки
        return amount * (percent / 100) * delayPeriod;
    }

    private double calculatePercent(double amount, Date calculationDate, long delayPeriod) {
        // ставка рефинансирования на дату рассчета
        RefinancingRate rate = refinancingRateReader.getRefinancingRareOnDate(calculationDate);
        // Проценты = долг * ставка нбрб/100 * дни/365
        return amount * (rate.getValue() / 100) * ((double) delayPeriod / 365);
    }

    private Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }
}
