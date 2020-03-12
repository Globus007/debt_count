package com.gleb.st.debt_count.service.calculator;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationData;

public interface DebtCalculationService {
    Calculation processCalculation(CalculationData calculationData);
}
