package com.gleb.st.debt_count.service.calculator;

import com.gleb.st.debt_count.component.calculator.DebtCalculator;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillHasPayments;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillNoPayments;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import org.springframework.stereotype.Component;

@Component
public class DebtCalculationServiceImpl implements DebtCalculationService {

    @Override
    public Calculation processCalculation(CalculationData calculationData) {

        DebtCalculator calculator;

        if (calculationData.getPayments().isEmpty()) {
            calculator =  new DebtCalculatorOneBillNoPayments(calculationData);
        } else {
            calculator = new DebtCalculatorOneBillHasPayments(calculationData);
        }

        return calculator.processCalculation();
    }
}
