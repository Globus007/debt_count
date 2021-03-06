package com.gleb.st.debt_count.refinancing;

import com.gleb.st.debt_count.entity.calculation.RefinancingRate;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
public class RefinancingRateTests {

    @Autowired
    private RefinancingRateReader refinancingRateReader;

    @Test
    public void refinancingRateOnDate20112019Test() {
        LocalDate date = LocalDate.parse("2019-11-20");
        RefinancingRate rate = refinancingRateReader.getRefinancingRateOnDate(date);
        Assertions.assertEquals(rate.getValue(), 9.0);
    }

    @Test
    public void refinancingRateOnDate19022020Test() {
        LocalDate date = LocalDate.parse("2020-02-19");
        RefinancingRate rate = refinancingRateReader.getRefinancingRateOnDate(date);
        Assertions.assertEquals(rate.getValue(), 8.75);
    }

    @Test
    public void refinancingRateOnDate03032020Test() {
        LocalDate date = LocalDate.parse("2020-03-03");
        RefinancingRate rate = refinancingRateReader.getRefinancingRateOnDate(date);
        Assertions.assertEquals(rate.getValue(), 8.75);
    }

}
