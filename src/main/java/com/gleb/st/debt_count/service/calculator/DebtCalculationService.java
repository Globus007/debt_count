package com.gleb.st.debt_count.service.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;

public interface DebtCalculationService {
    Calculation processCalculation(CalculationInputData calculationInputData);
}
