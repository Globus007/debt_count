package com.gleb.st.debt_count.service.calculator.factory;

import com.gleb.st.debt_count.component.calculator.DebtCalculator;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillHasPayments;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillNoPayments;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtCalculationFactory {

    @Autowired
    private DebtCalculatorOneBillHasPayments debtCalculatorOneBillHasPayments;
    @Autowired
    private DebtCalculatorOneBillNoPayments debtCalculatorOneBillNoPayments;

    public DebtCalculator getDebtCalculatorOneBillHasPayments(CalculationData calculationData) {
        return debtCalculatorOneBillHasPayments.setCalculationData(calculationData);
    }

    public DebtCalculator getDebtCalculatorOneBillNoPayments(CalculationData calculationData) {
        return debtCalculatorOneBillNoPayments.setCalculationData(calculationData);
    }
}
