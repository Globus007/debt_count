package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.debtor.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtCalculatorOneBillHasPayments extends DebtCalculator {

    public DebtCalculatorOneBillHasPayments(CalculationInputData calculationInputData) {
        super(calculationInputData);
    }

    @Override
    public Calculation processCalculation() {

        double totalFine = 0, totalPercent = 0, totalDebt = 0;
        //todo: NPE fix
        double contractFine = calculationInputData.getFine();
        List<String> calculationInfo = new ArrayList<>();
        StringBuilder info;

        for (Bill bill : calculationInputData.getBills()) {

            double debt = bill.getAmount();

            LocalDate startCountDate = bill.getPaymentDate();
            double refinancingRate, fine, percent;
            long expiration;

            info = new StringBuilder();
            info.append(String.format("ТТН № %s от %tF на сумму %.2f руб.",
                    bill.getNumber(),
                    bill.getDate(),
                    bill.getAmount()));
            calculationInfo.add(info.toString());

            for (Payment payment : bill.getPayments()) {
                info = new StringBuilder();
                info.append(String.format("Платеж %tF на сумму %.2f руб.\n",
                        payment.getDate(),
                        payment.getAmount()));

                expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
                fine = calculateFine(debt, contractFine, expiration);
                totalFine += fine;

                // adding counting info
                info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                        startCountDate,
                        payment.getDate(),
                        expiration));
                info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                        debt,
                        contractFine / 100,
                        expiration,
                        fine));

//                expiration = expirationCounter.calculateExpiration(bill.getPaymentDate(), payment.getDate());
                expiration = expirationCounter.calculateExpiration(bill.getPaymentDate(), payment.getDate());
                refinancingRate = refinancingRateReader.getRefinancingRateOnDate(payment.getDate()).getValue();
                percent = calculatePercent(payment.getAmount(), refinancingRate, expiration);
                totalPercent += percent;

                // adding counting info
                info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                        bill.getPaymentDate(),
                        payment.getDate(),
                        expiration));
                info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                        payment.getAmount(),
                        refinancingRate,
                        expiration,
                        percent));

                debt -= payment.getAmount();
                info.append(String.format("Долг = %.2f руб.", debt));

                startCountDate = payment.getDate().plusDays(1);
                calculationInfo.add(info.toString());
            }

            info = new StringBuilder();

            expiration = expirationCounter.calculateExpiration(startCountDate, calculationInputData.getCalculationDate());
            totalFine += calculateFine(debt, contractFine, expiration);

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    startCountDate,
                    calculationInputData.getCalculationDate(),
                    expiration));
            info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                    debt,
                    contractFine / 100,
                    expiration,
                    totalFine));

            expiration =
                    expirationCounter.calculateExpiration(bill.getPaymentDate(), calculationInputData.getCalculationDate());
            refinancingRate =
                    refinancingRateReader.getRefinancingRateOnDate(calculationInputData.getCalculationDate()).getValue();
            percent = calculatePercent(debt, refinancingRate, expiration);
            totalPercent += percent;

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    bill.getPaymentDate(),
                    calculationInputData.getCalculationDate(),
                    expiration));
            info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                    debt,
                    refinancingRate,
                    expiration,
                    percent));

            calculationInfo.add(info.toString());
            totalDebt += debt;
        }

        info = new StringBuilder();
        info.append(String.format("Итого задолженность составляет %.2f белорусских рублей:\n",
                totalDebt + totalPercent + totalFine));
        info.append(String.format("долг в размере %.2f руб.\n", totalDebt));
        info.append(String.format("пеня в размере %.2f руб.\n", totalFine));
        info.append(String.format("проценты в размере %.2f руб.", totalPercent));
        calculationInfo.add(info.toString());

        return new Calculation(totalDebt, totalFine, totalPercent, calculationInfo);
    }
}
