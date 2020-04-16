package com.gleb.st.debt_count.component.expiration.counter;

import com.gleb.st.debt_count.entity.calculation.Expiration;

import java.sql.Date;
import java.time.LocalDate;

public interface ExpirationCounter {
    Expiration calculateExpiration(LocalDate startDate, LocalDate finalDate);
}
