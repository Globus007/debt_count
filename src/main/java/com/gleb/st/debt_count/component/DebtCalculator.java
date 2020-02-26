package com.gleb.st.debt_count.component;

import com.gleb.st.debt_count.component.refinancing_rate.RefinancingRate;
import com.gleb.st.debt_count.component.refinancing_rate.RefinancingRateJsonReader;
import com.gleb.st.debt_count.entity.Bill;
import com.gleb.st.debt_count.entity.Debt;
import com.gleb.st.debt_count.entity.Payment;
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
        double penalty = bill.getAmount() * (debt.getPercent() / 100) * delayPeriod;

        return penalty;
    }

    public double calculatePenalty(Bill bill, Debt debt, Payment payment) {
        //        пеня считается на сумму всего долга до даты частичной оплаты,
        //        а после этой даты и до момента рассчета считается на (сумма долга - частичная оплата)
        long delayPeriodBeforePayment = countDelayPeriod(bill.getPayDate(), payment.getDate());
        double penaltyBeforePayment = payment.getAmount() * (debt.getPercent() / 100) * delayPeriodBeforePayment;

        long delayPeriodAfterPayment = countDelayPeriod(payment.getDate(), debt.getCalculationDate());
        double debtAfterPayment = bill.getAmount() - payment.getAmount();
        double penaltyAfterPayment = debtAfterPayment * (debt.getPercent() / 100) * delayPeriodAfterPayment;

        System.out.println("penaltyBeforePayment : " + penaltyBeforePayment);
        System.out.println("penaltyAfterPayment : " + penaltyAfterPayment);
        return penaltyBeforePayment + penaltyAfterPayment;
    }


    public double calculatePercent(Bill bill, Debt debt) {

        RefinancingRate refinancingRate = refinancingRateJsonReader.getRefinancingRareOnDate(debt.getCalculationDate());

        long delayPeriod = countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());

        //Проценты = долг * ставка нбрб / годовых т.е /365 * дни
        double debtPercent = bill.getAmount() * refinancingRate.getValue() / 365 * delayPeriod;

        return debtPercent;
    }

    public double calculatePercent(Bill bill, Debt debt, Payment payment) {
        //        Расчет процентов:
        //        До первой частичной оплаты по ставке установленной на день частичной оплаты
        //        сумму частичной оплаты * ставку на день частичной оплаты /365 * дни просрочки
        //        (с первого дня просрочки по день частичной оплаты)
        RefinancingRate refinancingRateOnPaymentDate =
                refinancingRateJsonReader.getRefinancingRareOnDate(payment.getDate());
        long delayPeriodBeforePayment = countDelayPeriod(bill.getPayDate(), payment.getDate());
        double debtPercentBeforePayment = payment.getAmount()
                * refinancingRateOnPaymentDate.getValue() / 365
                * delayPeriodBeforePayment;

        RefinancingRate refinancingRateOnCalculationDate =
                refinancingRateJsonReader.getRefinancingRareOnDate(debt.getCalculationDate());
        long delayPeriodAfterPayment = countDelayPeriod(payment.getDate(), debt.getCalculationDate());
        double debtAfterPayment = bill.getAmount() - payment.getAmount();
        double debtPercentAfterPayment = payment.getAmount()
                * refinancingRateOnPaymentDate.getValue() / 365
                * delayPeriodAfterPayment;

        System.out.println("debtPercentBeforePayment : " + debtPercentBeforePayment);
        System.out.println("debtPercentAfterPayment : " + debtPercentAfterPayment);
        return debtPercentBeforePayment + debtPercentAfterPayment;
    }

    public long countDelayPeriod(Date dateFrom, Date DateTo) {

        LocalDate payDate = LocalDate.parse(dateFrom.toString());
        LocalDate calculationDate = LocalDate.parse(DateTo.toString());

        return DAYS.between(payDate, calculationDate);
    }


}
