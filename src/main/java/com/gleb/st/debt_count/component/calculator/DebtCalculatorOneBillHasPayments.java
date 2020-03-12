package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import com.gleb.st.debt_count.entity.calculation.Expiration;
import com.gleb.st.debt_count.entity.debtor.Payment;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DebtCalculatorOneBillHasPayments extends DebtCalculator {

    public DebtCalculatorOneBillHasPayments(CalculationData calculationData) {
        super(calculationData);
    }

    @Override
    public Calculation processCalculation() {

        double debt = calculationData.getBills().get(0).getAmount();

        double fine = 0, percent = 0;

        // todo: contract may be null. Then paymentDate = 2 days after bill date
        Date startCountDate = calculationData.getContract().getPaymentDate();
        Expiration expiration;

        //todo sort payments on payment date
        for (Payment payment: calculationData.getPayments()) {
            expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
            fine += calculateFine(debt, calculationData.getContract().getFine(), expiration.getValue());

            expiration = expirationCounter.calculateExpiration(calculationData.getContract().getPaymentDate(), payment.getDate());
            percent += calculatePercent(payment.getAmount(), payment.getDate(), expiration.getValue());

            debt -= payment.getAmount();
            startCountDate = getNextDay(payment.getDate());
        }

        expiration = expirationCounter.calculateExpiration(startCountDate, calculationData.getCalculationDate());
        fine += calculateFine(debt, calculationData.getContract().getFine(), expiration.getValue());

        expiration = expirationCounter.calculateExpiration(calculationData.getContract().getPaymentDate(), calculationData.getCalculationDate());
        percent += calculatePercent(debt, calculationData.getCalculationDate(), expiration.getValue());

        // todo: Ставка пени может быть ограничена. Например ставка0.15% но не более 10% от долга
        return new Calculation(debt, fine, percent);
    }
}
