package com.gleb.st.debt_count.component.refinancing.rate.reader;

import com.gleb.st.debt_count.entity.calculation.RefinancingRate;

import java.time.LocalDate;

public interface RefinancingRateReader {
    RefinancingRate getRefinancingRateOnDate(LocalDate date);
}
