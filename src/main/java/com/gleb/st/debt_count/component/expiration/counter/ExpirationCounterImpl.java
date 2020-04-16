package com.gleb.st.debt_count.component.expiration.counter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class ExpirationCounterImpl implements ExpirationCounter {

    @Override
    public long calculateExpiration(LocalDate startDate, LocalDate finalDate) {
        return DAYS.between(startDate, finalDate) + 1;
    }

    // todo: calculateExpiration with праздничные рабочие дней и переносы
}
