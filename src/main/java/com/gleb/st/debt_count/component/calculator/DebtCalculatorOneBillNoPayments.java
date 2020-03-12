package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import com.gleb.st.debt_count.entity.calculation.Expiration;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DebtCalculatorOneBillNoPayments extends DebtCalculator {

    public DebtCalculatorOneBillNoPayments(CalculationData calculationData) {
        super(calculationData);
    }

    @Override
    public Calculation processCalculation() {

        Bill bill = calculationData.getBills().get(0);
        Date calculationDate = calculationData.getCalculationDate();

        // todo: contract may be null. Then paymentDate = 2 days after bill date
        Date paymentDate = calculationData.getContract().getPaymentDate();
        double contractFine = calculationData.getContract().getFine();

        Expiration expiration = expirationCounter.calculateExpiration(paymentDate, calculationDate);

        double fine = calculateFine(bill.getAmount(), contractFine, expiration.getValue());
        double percent = calculatePercent(bill.getAmount(), calculationDate, expiration.getValue());

        return new Calculation(bill.getAmount(), fine, percent);
    }
}
