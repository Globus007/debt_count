package com.gleb.st.debt_count.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gleb.st.debt_count.controller.rest.CalculationController;
import com.gleb.st.debt_count.entity.calculation.Calculation;
import com.gleb.st.debt_count.entity.calculation.CalculationInputData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * долг 22 138,00
 * оплаты:
 * 26.11.2019 – 4 305,19
 * 03.12.2019 – 1818,18 руб.
 * 19.12.2019 – 1818,18 руб.
 * 26.12.2019 – 1818,19 руб.
 * 03.01.2020 – 12378,26 руб.
 *
 * срок оплаты до 01.03.2019
 *
 * Итого проценты = 1 616,73 белорусских рублей.
 * только % пени нет
 */

@SpringBootTest
public class SomeDebtCalculationTests {

    private final String DATA = """
                        {
                         "bills":
                           [{
                             "amount":22138.0,
                             "paymentDate":"2019-03-02",
                             "payments":
                               [{
                                 "amount":"4305.19",
                                 "date":"2019-11-26"
                               },
                               {
                                 "amount":"1818.18",
                                 "date":"2019-12-03"
                               },
                               {
                                 "amount":"1818.18",
                                 "date":"2019-12-19"
                               },
                               {
                                 "amount":"1818.18",
                                 "date":"2019-12-26"
                               },
                               {
                                 "amount":"12378.26",
                                 "date":"2020-01-03"
                               }]
                           }],
                         "calculationDate":"2020-05-13"
                        }
                        """;

    @Autowired
    private CalculationController controller;

    private Calculation processCalculation() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return controller.makeCalculation(mapper.readValue(DATA, CalculationInputData.class));
    }

    @Test
    public void percentCalculationTest() throws JsonProcessingException {
        Calculation calculation = processCalculation();
        Assert.assertEquals(1_616.73, calculation.getPercent(), 0.01);
    }

}
