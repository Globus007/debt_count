package com.gleb.st.debt_count.calculator;

import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.debtor.Contract;
import com.gleb.st.debt_count.entity.debtor.Payment;
import com.gleb.st.debt_count.service.calculator.DebtCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DebtCalculationServiceOneBillTreePaymentsTests {

    @Autowired
    private DebtCalculationService calculator;

    private Calculation calculation;

    /*
        ТТН № 1038486 от 13.05.2019 г.  на сумму 10 606,31 руб.

        Платеж 28.06.2019 на сумму 1 000,00 руб.
        С 28.05.2019 по 28.06.2019 – 32 дня просрочки
        Пеня = 10 606,31 х 0,15% х 32 = 509,10 руб.
        Проценты = 1 000,00 х 10 % х 32 /365 = 8,77 руб.
        Долг = 9 606,31 руб.

        Платеж 26.07.2019 на сумму 500,00 руб.
        С 29.06.2019 по 26.07.2019 – 28 дней просрочки
        Пеня = 9 606,31 х 0,15% х 28 = 403,47 руб.
        С 28.05.2019 по 26.07.2019 – 60 дней просрочки
        Проценты = 500,00 х 10 % х 60 /365 = 8,22 руб.
        Долг = 9 106,31 руб.

        Платеж 13.08.2019 на сумму 500,00 руб.
        С 27.07.2019 по 13.08.2019 – 18 дней просрочки
        Пеня = 9 106,31 х 0,15% х 18 = 245,87 руб.
        С 28.05.2019 по 13.08.2019 – 78 дней просрочки
        Проценты = 500,00 х 10 % х 78 /365 = 10,68 руб.
        Долг = 8 606,31 руб.

        С 14.08.2019 по 16.09.2019 – 34 дня просрочки
        Пеня = 8 606,31 х 0,15% х 34 = 438,92 руб.
        С 28.05.2019 по 16.09.2019 – 112 дней просрочки
        Проценты = 8 606,31 х 9.5 % х 112 /365 = 250,88 руб.

        Итого задолженность составляет 10482.22 белорусских рублей:
        долг в размере 8 606,31 руб.
        пеня в размере 1 597,36 руб.
        проценты в размере 278.55 руб.
     */

    private Calculation processCalculation() {
        CalculationInputData calculationInputData = new CalculationInputData();

        Contract contract = new Contract();
        contract.setFine(0.15);
        calculationInputData.setContract(contract);

        List<Bill> bills = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(Date.valueOf("2019-06-28"), 1_000.00));
        payments.add(new Payment(Date.valueOf("2019-07-26"), 500.00));
        payments.add(new Payment(Date.valueOf("2019-08-13"), 500.00));

        Bill bill = new Bill("1038486", Date.valueOf("2019-05-13"), 10_606.31);
        bill.setPaymentDate(Date.valueOf("2019-05-28"));
        bill.setPayments(payments);
        bills.add(bill);
        calculationInputData.setBills(bills);

        calculationInputData.setCalculationDate(Date.valueOf("2019-09-16"));


        calculation = calculator.processCalculation(calculationInputData);
        return calculation;
    }

    @Test
    public void calculationFullDebtTest() {
        Calculation calculation = processCalculation();
        Assertions.assertEquals(10_482.22, calculation.getFullDebt(), 0.01);
    }

    @Test
    public void calculationDebtTest() {
        Calculation calculation = processCalculation();
        Assertions.assertEquals(8_606.31, calculation.getDebt(), 0.01);
    }

    @Test
    public void calculationFineTest() {
        Calculation calculation = processCalculation();
        Assertions.assertEquals(1_597.36, calculation.getFine(), 0.01);
    }

    @Test
    public void calculationPercentTest() {
        Calculation calculation = processCalculation();
        Assertions.assertEquals(278.55, calculation.getPercent(), 0.01);
    }

    @Test
    public void finalCalculationInfoTest() {
        Calculation calculation = processCalculation();
        String firstInfo = calculation.getCalculatingInfo().get(0);
        Assertions.assertEquals("ТТН № 1038486 от 2019-05-13 на сумму 10606,31 руб.", firstInfo);
    }

    @Test
    public void firstCalculationInfoTest() {
        Calculation calculation = processCalculation();
        int size = calculation.getCalculatingInfo().size();
        String lastInfo = calculation.getCalculatingInfo().get(size-1);
        Assertions.assertEquals(
                "Итого задолженность составляет 10482,22 белорусских рублей:\n" +
                "долг в размере 8606,31 руб.\n" +
                "пеня в размере 1597,36 руб.\n" +
                "проценты в размере 278,55 руб.",
                lastInfo);
    }
}
