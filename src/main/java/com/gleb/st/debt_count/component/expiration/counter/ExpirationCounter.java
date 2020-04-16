package com.gleb.st.debt_count.component.expiration.counter;

import java.time.LocalDate;

public interface ExpirationCounter {
    long calculateExpiration(LocalDate startDate, LocalDate finalDate);
}
