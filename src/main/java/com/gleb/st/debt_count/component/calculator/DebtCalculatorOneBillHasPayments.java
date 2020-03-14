package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import com.gleb.st.debt_count.entity.calculation.Expiration;
import com.gleb.st.debt_count.entity.debtor.Payment;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtCalculatorOneBillHasPayments extends DebtCalculator {

    public DebtCalculatorOneBillHasPayments(CalculationData calculationData) {
        super(calculationData);
    }

    @Override
    public Calculation processCalculation() {

        double debt = calculationData.getBills().get(0).getAmount();

        double fine = 0, percent = 0;
        double contractFine = calculationData.getContract().getFine();

        // todo: contract may be null. Then paymentDate = 2 days after bill date
        Date startCountDate = calculationData.getContract().getPaymentDate();
        Expiration expiration;
        List<String> calculationInfo = new ArrayList<>();

        //todo: create properties for formatted strings
        for (Payment payment: calculationData.getPayments()) {
            StringBuilder info = new StringBuilder();
            info.append(String.format("Платеж %tF на сумму %.2f руб.\n",
                    payment.getDate(),
                    payment.getAmount()));

            expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    startCountDate,
                    payment.getDate(),
                    expiration.getValue()));
            fine += calculateFine(
                    debt,
                    contractFine,
                    expiration.getValue());
            info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                    debt,
                    contractFine/100,
                    expiration.getValue(),
                    fine));

            expiration = expirationCounter.calculateExpiration(
                    calculationData.getContract().getPaymentDate(),
                    payment.getDate());
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    calculationData.getContract().getPaymentDate(),
                    payment.getDate(),
                    expiration.getValue()));
            percent += calculatePercent(
                    payment.getAmount(),
                    payment.getDate(),
                    expiration.getValue());
            info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                    payment.getAmount(),
                    //todo: refactor code: Refinancing Rate should call once
                    refinancingRateReader.getRefinancingRateOnDate(payment.getDate()).getValue(),
                    expiration.getValue(),
                    percent));

            debt -= payment.getAmount();
            info.append(String.format("Долг = %.2f руб.", debt));

            startCountDate = getNextDay(payment.getDate());
            calculationInfo.add(info.toString());
        }

        StringBuilder info = new StringBuilder();

        expiration = expirationCounter.calculateExpiration(startCountDate, calculationData.getCalculationDate());
        info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                startCountDate,
                calculationData.getCalculationDate(),
                expiration.getValue()));
        fine += calculateFine(
                debt,
                contractFine,
                expiration.getValue());
        info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                debt,
                contractFine/100,
                expiration.getValue(),
                fine));

        expiration = expirationCounter.calculateExpiration(
                calculationData.getContract().getPaymentDate(),
                calculationData.getCalculationDate());
        info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                calculationData.getContract().getPaymentDate(),
                calculationData.getCalculationDate(),
                expiration.getValue()));
        percent += calculatePercent(
                debt,
                calculationData.getCalculationDate(),
                expiration.getValue());
        info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                debt,
                //todo: refactor code: Refinancing Rate should call once
                refinancingRateReader.getRefinancingRateOnDate(calculationData.getCalculationDate()).getValue(),
                expiration.getValue(),
                percent));

        calculationInfo.add(info.toString());

        info = new StringBuilder();
        info.append(String.format("Итого задолженность составляет %.2f белорусских рублей:\n",
                debt + percent + fine));
        info.append(String.format("долг в размере %.2f руб.\n", debt));
        info.append(String.format("пеня в размере %.2f руб.\n", fine));
        info.append(String.format("проценты в размере %.2f руб.\n", percent));
        calculationInfo.add(info.toString());

        // todo: Ставка пени может быть ограничена. Например ставка0.15% но не более 10% от долга
        return new Calculation(debt, fine, percent, calculationInfo);
    }
}
