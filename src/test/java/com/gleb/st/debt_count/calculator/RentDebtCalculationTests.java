package com.gleb.st.debt_count.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.service.calculator.DebtCalculationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Арендная плата за июль 2019
 * Арендная плата за август 2019
 * Арендная плата за сентябрь 2019
 * Арендная плата за октябрь 2019
 * Арендная плата за ноябрь 2019
 * Арендная плата за декабрь 2019
 * 865,10 руб.
 * срок оплаты - до 10 числа месяца, следующего за отчетным
 * пеня 0,3
 *
 *
 * РАСЧЕТ:
 * Арендная плата за июль 2019 г.
 * С 11.08.2019 по 15.04.2020 просрочка 249 дней
 * Пеня = 865,10 х 0,3% х 249 = 646,23 руб.
 *
 * Арендная плата за август 2019 г.
 * С 11.09.2019 по 15.04.2020 просрочка 218 дней
 * Пеня = 865,10 х 0,3% х 218 = 565,78 руб.
 *
 * Арендная плата за сентябрь 2019 г.
 * С 11.10.2019 по 15.04.2020 просрочка 188 дней
 * Пеня = 865,10 х 0,3% х 188 = 487,92 руб.
 *
 * Арендная плата за октябрь 2019 г.
 * С 11.11.2019 по 15.04.2020 просрочка 157 дней
 * Пеня = 865,10 х 0,3% х 157 = 407,46 руб.
 *
 * Арендная плата за ноябрь 2019 г.
 * С 11.12.2019 по 15.04.2020 просрочка 127 дней
 * Пеня = 865,10 х 0,3% х 127 = 329,60 руб.
 *
 * Арендная плата за декабрь 2019 г.
 * С 11.01.2020 по 15.04.2020 просрочка 96 дней
 * Пеня = 865,10 х 0,3% х 96 = 249,15 руб.
 *
 * Итого пеня = 2 686,14 руб.
 */

@SpringBootTest
public class RentDebtCalculationTests {

    @Autowired
    private DebtCalculationService calculationService;

    private Calculation processCalculation() {
        CalculationInputData inputData = new CalculationInputData();
        List<Bill> bills = new ArrayList<>(6);

        LocalDate date = LocalDate.parse("2019-08-11");
        for (int i = 0; i < 6; i++) {
            Bill bill = new Bill();
            bill.setPaymentDate(date);
            bill.setAmount(865.1);
            bill.setPayments(new ArrayList<>());

            bills.add(bill);
            date = date.plusMonths(1);
        }

        inputData.setBills(bills);

        inputData.setCalculationDate(LocalDate.parse("2020-04-15"));

        inputData.setFine(0.3);

        return calculationService.processCalculation(inputData);
    }

    @Test
    public void fineTest() {
        Calculation calculation = processCalculation();
        Assert.assertEquals(2_686.14, calculation.getFine(), 0.01);
    }
}
