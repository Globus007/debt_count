package com.gleb.st.debt_count.component;

import com.gleb.st.debt_count.component.refinancing.rate.RefinancingRate;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
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

    private final RefinancingRateReader refinancingRateReader;

    @Autowired
    public DebtCalculator(RefinancingRateReader refinancingRateReader) {
        this.refinancingRateReader = refinancingRateReader;
    }

    public double calculatePenalty(Bill bill, Debt debt) {

        // дни просрочки
        long delayPeriod = countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());

        // Пеня = долг * ставка * дни просрочки
        return bill.getAmount() * (debt.getPercent() / 100) * delayPeriod;
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

        // ставка рефинансирования на дату рассчета
        RefinancingRate rate = refinancingRateReader.getRefinancingRareOnDate(debt.getCalculationDate());

        // дни просрочки
        long delayPeriod = countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());

        // Проценты = долг * ставка нбрб годовых/100 /365 * дни
        return bill.getAmount() * rate.getValue() / 365 * delayPeriod / 100;
    }

    public double calculatePercent(Bill bill, Debt debt, Payment payment) {
        //        Расчет процентов:
        //        До первой частичной оплаты по ставке установленной на день частичной оплаты
        //        сумму частичной оплаты * ставку на день частичной оплаты /365 * дни просрочки
        //        (с первого дня просрочки по день частичной оплаты)
        RefinancingRate refinancingRateOnPaymentDate =
                refinancingRateReader.getRefinancingRareOnDate(payment.getDate());
        long delayPeriodBeforePayment = countDelayPeriod(bill.getPayDate(), payment.getDate());
        double debtPercentBeforePayment = payment.getAmount()
                * refinancingRateOnPaymentDate.getValue() / 365
                * delayPeriodBeforePayment;

        RefinancingRate refinancingRateOnCalculationDate =
                refinancingRateReader.getRefinancingRareOnDate(debt.getCalculationDate());
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

        // last day included
        return DAYS.between(payDate, calculationDate) + 1;
    }
}
