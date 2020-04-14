package com.gleb.st.debt_count.service.calculator.factory;

import com.gleb.st.debt_count.component.calculator.DebtCalculator;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillHasPayments;
import com.gleb.st.debt_count.component.calculator.DebtCalculatorOneBillNoPayments;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtCalculationFactory {

    @Autowired
    private DebtCalculatorOneBillHasPayments debtCalculatorOneBillHasPayments;
    @Autowired
    private DebtCalculatorOneBillNoPayments debtCalculatorOneBillNoPayments;

    public DebtCalculator getDebtCalculatorOneBillHasPayments(CalculationInputData calculationInputData) {
        return debtCalculatorOneBillHasPayments.setCalculationInputData(calculationInputData);
    }

    public DebtCalculator getDebtCalculatorOneBillNoPayments(CalculationInputData calculationInputData) {
        return debtCalculatorOneBillNoPayments.setCalculationInputData(calculationInputData);
    }
}
