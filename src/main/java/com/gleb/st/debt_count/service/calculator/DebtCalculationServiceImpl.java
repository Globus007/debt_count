package com.gleb.st.debt_count.service.calculator;

import com.gleb.st.debt_count.component.calculator.DebtCalculator;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import com.gleb.st.debt_count.service.calculator.factory.DebtCalculationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebtCalculationServiceImpl implements DebtCalculationService {

    @Autowired
    DebtCalculationFactory debtCalculationFactory;

    @Override
    public Calculation processCalculation(CalculationData calculationData) {

        DebtCalculator calculator;

        if (calculationData.getPayments().isEmpty()) {
            calculator =  debtCalculationFactory.getDebtCalculatorOneBillNoPayments(calculationData);
        } else {
            calculator = debtCalculationFactory.getDebtCalculatorOneBillHasPayments(calculationData);
        }

        return calculator.processCalculation();
    }
}
