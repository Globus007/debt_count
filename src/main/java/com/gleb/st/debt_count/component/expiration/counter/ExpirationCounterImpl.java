package com.gleb.st.debt_count.component.expiration.counter;

import com.gleb.st.debt_count.entity.calculation.Expiration;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class ExpirationCounterImpl implements ExpirationCounter {

    @Override
    public Expiration calculateExpiration(LocalDate startDate, LocalDate finalDate) {

         LocalDate payDate = LocalDate.parse(startDate.toString());
         LocalDate calculationDate = LocalDate.parse(finalDate.toString());
         // last day included
        long expirationPeriod = DAYS.between(payDate, calculationDate) + 1;

        return new Expiration(expirationPeriod);
    }

    // todo: calculateExpiration with праздничные рабочие дней и переносы
}
