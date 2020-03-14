package com.gleb.st.debt_count.controller.rest;

import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.service.calculator.DebtCalculationService;
import com.gleb.st.debt_count.entity.calculation.CalculationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://debt-calculator-frontend.herokuapp.com")
@RestController
public class CalculationController {

    @Autowired
    DebtCalculationService calculator;

    @PostMapping("/makeCalculation")
    public Calculation makeCalculation(@RequestBody CalculationData calculationData) {
        System.out.println(calculationData);
        return calculator.processCalculation(calculationData);
    }
}
