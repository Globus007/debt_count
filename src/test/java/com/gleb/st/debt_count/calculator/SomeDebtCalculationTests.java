package com.gleb.st.debt_count.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.debtor.Payment;
import com.gleb.st.debt_count.service.calculator.DebtCalculationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * долг 22 138,00
 * оплаты:
 * 26.11.2019 – 4 305,19
 * 03.12.2019 – 1818,18 руб.
 * 19.12.2019 – 1818,18 руб.
 * 26.12.2019 – 1818,19 руб.
 * 03.01.2020 – 12378,26 руб.
 *
 * срок оплаты до 01.03.2019
 *
 * Итого проценты = 1 616,73 белорусских рублей.
 * только % пени нет
 */

@SpringBootTest
public class SomeDebtCalculationTests {

    @Autowired
    private DebtCalculationService calculationService;

    private Calculation processCalculation() {
        CalculationInputData inputData = new CalculationInputData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        List<Payment> payments = new ArrayList<>(5);
        payments.add(new Payment(LocalDate.parse("26.11.2019", formatter), 4_305.19));
        payments.add(new Payment(LocalDate.parse("03.12.2019", formatter), 1_818.18));
        payments.add(new Payment(LocalDate.parse("19.12.2019", formatter), 1_818.18));
        payments.add(new Payment(LocalDate.parse("26.12.2019", formatter), 1_818.18));
        payments.add(new Payment(LocalDate.parse("03.01.2020", formatter), 12_378.26));

        Bill bill = new Bill();
        bill.setAmount(22_138.0);
        bill.setPayments(payments);
        bill.setPaymentDate(LocalDate.parse("02.03.2019", formatter));

        List<Bill> bills = new ArrayList<>(1);
        bills.add(bill);

        inputData.setBills(bills);

        inputData.setCalculationDate(LocalDate.now());

        return calculationService.processCalculation(inputData);
    }

    @Test
    public void percentCalculationTest() {
        Calculation calculation = processCalculation();
        Assert.assertEquals(1_616.73, calculation.getPercent(), 0.01);
    }

}
