package com.gleb.st.debt_count.component.expiration.counter;

import com.gleb.st.debt_count.entity.calculation.Expiration;

import java.sql.Date;

public interface ExpirationCounter {
    Expiration calculateExpiration(Date startDate, Date finalDate);
}
