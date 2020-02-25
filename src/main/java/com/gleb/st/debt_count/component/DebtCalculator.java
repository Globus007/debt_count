package com.gleb.st.debt_count.component;

import com.gleb.st.debt_count.component.refinancing_rate.RefinancingRate;
import com.gleb.st.debt_count.component.refinancing_rate.RefinancingRateJsonReader;
import com.gleb.st.debt_count.entity.Bill;
import com.gleb.st.debt_count.entity.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class DebtCalculator {

    // todo: inject from property file
    private static final String DECIMAL_FORMAT = "#0.00";

    @Autowired
    private RefinancingRateJsonReader refinancingRateJsonReader;

    public double calculatePenalty(Bill bill, Debt debt) {

        // считаем дни просрочки
        long delayPeriod = countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());

        // считаем пеню по формуле : Пеня = долг * ставка * дни просрочки
        double penalty = bill.getAmount() * (debt.getPercent() / 100) * delayPeriod ;

        return penalty;
    }

    public double calculatePercent(Bill bill, Debt debt) {

        java.util.Date today = new java.util.Date();
        RefinancingRate refinancingRate = refinancingRateJsonReader.getRefinancingRareOnDate(new Date(today.getTime()));

        long delayPeriod = countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());

        //Проценты = долг * ставка нбрб / годовых т.е /365 * дни
        double debtPercent = bill.getAmount() * refinancingRate.getValue() / 365 * delayPeriod;

        return debtPercent;
    }

    public long countDelayPeriod(Date dateFrom, Date DateTo) {

        LocalDate payDate = LocalDate.parse(dateFrom.toString());
        LocalDate calculationDate = LocalDate.parse(DateTo.toString());

        return DAYS.between(payDate, calculationDate);
    }

}
