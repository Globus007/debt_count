package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.calculation.Expiration;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.debtor.Payment;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtCalculatorOneBillHasPayments extends DebtCalculator {

    public DebtCalculatorOneBillHasPayments(CalculationInputData calculationInputData) {
        super(calculationInputData);
    }

    @Override
    public Calculation processCalculation() {

        Bill bill = calculationInputData.getBills().get(0);
        double debt = bill.getAmount();
        double contractFine = calculationInputData.getContract().getFine();
        Date startCountDate = bill.getPaymentDate();
        double totalFine = 0, totalPercent = 0, refinancingRate, fine, percent;
        List<String> calculationInfo = new ArrayList<>();
        Expiration expiration;

        StringBuilder info = new StringBuilder();
        info.append(String.format("ТТН № %s от %tF на сумму %.2f руб.",
                bill.getNumber(),
                bill.getDate(),
                bill.getAmount()));
        calculationInfo.add(info.toString());

        //todo: create properties for formatted strings
        for (Payment payment: bill.getPayments()) {
            info = new StringBuilder();
            info.append(String.format("Платеж %tF на сумму %.2f руб.\n",
                    payment.getDate(),
                    payment.getAmount()));

            expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
            fine = calculateFine(debt, contractFine, expiration.getValue());
            totalFine += fine;

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    startCountDate,
                    payment.getDate(),
                    expiration.getValue()));
            info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                    debt,
                    contractFine/100,
                    expiration.getValue(),
                    fine));

            expiration = expirationCounter.calculateExpiration(bill.getPaymentDate(), payment.getDate());
            refinancingRate = refinancingRateReader.getRefinancingRateOnDate(payment.getDate()).getValue();
            percent = calculatePercent(payment.getAmount(), refinancingRate, expiration.getValue());
            totalPercent += percent;

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    bill.getPaymentDate(),
                    payment.getDate(),
                    expiration.getValue()));
            info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                    payment.getAmount(),
                    refinancingRate,
                    expiration.getValue(),
                    percent));

            debt -= payment.getAmount();
            info.append(String.format("Долг = %.2f руб.", debt));

            startCountDate = getNextDay(payment.getDate());
            calculationInfo.add(info.toString());
        }

        info = new StringBuilder();

        expiration = expirationCounter.calculateExpiration(startCountDate, calculationInputData.getCalculationDate());
        totalFine += calculateFine(debt, contractFine, expiration.getValue());
        info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                startCountDate,
                calculationInputData.getCalculationDate(),
                expiration.getValue()));
        info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                debt,
                contractFine/100,
                expiration.getValue(),
                totalFine));

        expiration =
                expirationCounter.calculateExpiration(bill.getPaymentDate(), calculationInputData.getCalculationDate());
        refinancingRate =
                refinancingRateReader.getRefinancingRateOnDate(calculationInputData.getCalculationDate()).getValue();
        percent = calculatePercent(debt, refinancingRate, expiration.getValue());
        totalPercent += percent;

        info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                bill.getPaymentDate(),
                calculationInputData.getCalculationDate(),
                expiration.getValue()));
        info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                debt,
                refinancingRate,
                expiration.getValue(),
                percent));

        calculationInfo.add(info.toString());

        info = new StringBuilder();
        info.append(String.format("Итого задолженность составляет %.2f белорусских рублей:\n",
                debt + totalPercent + totalFine));
        info.append(String.format("долг в размере %.2f руб.\n", debt));
        info.append(String.format("пеня в размере %.2f руб.\n", totalFine));
        info.append(String.format("проценты в размере %.2f руб.", totalPercent));
        calculationInfo.add(info.toString());

        // todo: Ставка пени может быть ограничена. Например ставка0.15% но не более 10% от долга
        return new Calculation(debt, totalFine, totalPercent, calculationInfo);
    }
}
