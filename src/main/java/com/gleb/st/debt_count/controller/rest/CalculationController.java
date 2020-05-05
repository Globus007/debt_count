package com.gleb.st.debt_count.controller.rest;

import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateJsonReader;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.service.calculator.DebtCalculationService;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://debt-calculator-frontend.herokuapp.com")
@RestController
public class CalculationController {

    private final static Logger log = Logger.getLogger(RefinancingRateJsonReader.class.getName());

    @Autowired
    DebtCalculationService calculator;

    @PostMapping("/makeCalculation")
    public Calculation makeCalculation(@RequestBody CalculationInputData calculationInputData) {
        log.info("Input data >>>> " + calculationInputData);
        return calculator.processCalculation(calculationInputData);
    }
}
