package com.gleb.st.debt_count.component.calculator;

import com.gleb.st.debt_count.entity.Bill;
import com.gleb.st.debt_count.entity.Calculation;
import com.gleb.st.debt_count.entity.Contract;
import com.gleb.st.debt_count.entity.Payment;

import java.util.List;

public interface DebtCalculator {

    Calculation processCalculation(Contract contract, Bill bill);
    Calculation processCalculation(Contract contract, Bill bill, List<Payment> payments);
    Calculation processCalculation(Contract contract, List<Bill> bills, List<Payment> payments);

}
