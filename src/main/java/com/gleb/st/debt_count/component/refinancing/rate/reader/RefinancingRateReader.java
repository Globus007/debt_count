package com.gleb.st.debt_count.component.refinancing.rate.reader;

import com.gleb.st.debt_count.entity.RefinancingRate;

import java.sql.Date;

public interface RefinancingRateReader {
    RefinancingRate getRefinancingRareOnDate(Date date);
}
