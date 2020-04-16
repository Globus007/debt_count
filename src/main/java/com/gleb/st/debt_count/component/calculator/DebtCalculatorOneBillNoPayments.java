package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtCalculatorOneBillNoPayments extends DebtCalculator {

    public DebtCalculatorOneBillNoPayments(CalculationInputData inputData) {
        super(inputData);
    }

    @Override
    public Calculation processCalculation() {

        Bill bill = inputData.getBills().get(0);
        LocalDate calculationDate = inputData.getCalculationDate();
        List<String> calculationInfo = new ArrayList<>();
        StringBuilder info = new StringBuilder();

        LocalDate paymentDate = inputData.getContract().getPaymentDate();
        double contractFine = inputData.getFine();

        long expiration = expirationCounter.calculateExpiration(paymentDate, calculationDate);
        info.append(String.format("C %tF по %tF - %d дня просрочки\n",
                paymentDate,
                calculationDate,
                expiration));

        double fine = calculateFine(
                bill.getAmount(),
                contractFine,
                expiration);
        info.append(String.format("Пеня = %.2f х %.2f%% х %d = %.2f руб.\n",
                bill.getAmount(),
                contractFine/100,
                expiration,
                fine));

        double refinancingRate = refinancingRateReader.
                getRefinancingRateOnDate(calculationDate)
                .getValue();
        double percent = calculatePercent(
                bill.getAmount(),
                refinancingRate,
                expiration);
        info.append(String.format("Проценты = %.2f х %.2f%% х %d /365 = %.2f руб.\n",
                bill.getAmount(),
                refinancingRate,
                expiration,
                percent));
        calculationInfo.add(info.toString());

        info = new StringBuilder();
        info.append(String.format("Итого задолженность составляет %.2f белорусских рублей:\n",
                bill.getAmount() + percent + fine));
        info.append(String.format("долг в размере %.2f руб.\n", bill.getAmount()));
        info.append(String.format("пеня в размере %.2f руб.\n", fine));
        info.append(String.format("проценты в размере %.2f руб.", percent));
        calculationInfo.add(info.toString());

        return new Calculation(bill.getAmount(), fine, percent, calculationInfo);
    }
}
