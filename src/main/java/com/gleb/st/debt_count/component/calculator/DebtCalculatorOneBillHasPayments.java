package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.debtor.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtCalculatorOneBillHasPayments extends DebtCalculator {

    @Value("${date.format}")
    private String dateFormat;

    public DebtCalculatorOneBillHasPayments(CalculationInputData calculationInputData) {
        super(calculationInputData);
    }

    @Override
    public Calculation processCalculation() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

        double totalFine = 0, totalPercent = 0, totalDebt = 0;
        //todo: NPE fix
        double contractFine = inputData.getFine();
        List<String> calculationInfo = new ArrayList<>();
        StringBuilder info;

        for (Bill bill : inputData.getBills()) {

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
                info.append(String.format("Платеж %s на сумму %.2f руб.\n",
                        payment.getDate().format(dateFormatter),
                        payment.getAmount()));

                expiration = expirationCounter.calculateExpiration(startCountDate, payment.getDate());
                fine = calculateFine(debt, contractFine, expiration);
                totalFine += fine;

                // adding counting info
                info.append(String.format("C %s по %s - %d дня просрочки\n",
                        startCountDate.format(dateFormatter),
                        payment.getDate().format(dateFormatter),
                        expiration));
                info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                        debt,
                        contractFine,
                        expiration,
                        fine));

                expiration = expirationCounter.calculateExpiration(bill.getPaymentDate(), payment.getDate());
                refinancingRate = refinancingRateReader.getRefinancingRateOnDate(payment.getDate()).getValue();
                percent = calculatePercent(payment.getAmount(), refinancingRate, expiration);
                totalPercent += percent;

                // adding counting info
                info.append(String.format("C %s по %s - %d дня просрочки\n",
                        bill.getPaymentDate().format(dateFormatter),
                        payment.getDate().format(dateFormatter),
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

            expiration = expirationCounter.calculateExpiration(startCountDate, inputData.getCalculationDate());
            totalFine += calculateFine(debt, contractFine, expiration);

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    startCountDate,
                    inputData.getCalculationDate(),
                    expiration));
            info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                    debt,
                    contractFine,
                    expiration,
                    totalFine));

            expiration =
                    expirationCounter.calculateExpiration(bill.getPaymentDate(), inputData.getCalculationDate());
            refinancingRate =
                    refinancingRateReader.getRefinancingRateOnDate(inputData.getCalculationDate()).getValue();
            percent = calculatePercent(debt, refinancingRate, expiration);
            totalPercent += percent;

            // adding counting info
            info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                    bill.getPaymentDate(),
                    inputData.getCalculationDate(),
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
