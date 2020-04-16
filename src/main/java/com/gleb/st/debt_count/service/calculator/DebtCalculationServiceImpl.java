package com.gleb.st.debt_count.service.calculator;

import com.gleb.st.debt_count.component.calculator.DebtCalculator;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import com.gleb.st.debt_count.service.calculator.factory.DebtCalculationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebtCalculationServiceImpl implements DebtCalculationService {

    @Autowired
    DebtCalculationFactory debtCalculationFactory;

    @Override
    public Calculation processCalculation(CalculationInputData calculationInputData) {

        DebtCalculator debtCalculator;

//        if (calculationInputData.getPayments() == null || calculationInputData.getPayments().isEmpty()) {
//            debtCalculator =  debtCalculationFactory.getDebtCalculatorOneBillNoPayments(calculationInputData);
//        } else {
//            debtCalculator = debtCalculationFactory.getDebtCalculatorOneBillHasPayments(calculationInputData);
//        }

        debtCalculator = debtCalculationFactory.getDebtCalculatorOneBillHasPayments(calculationInputData);



        return debtCalculator.processCalculation();
    }
}
