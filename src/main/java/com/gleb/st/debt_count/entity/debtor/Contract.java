package com.gleb.st.debt_count.entity.debtor;

import java.time.LocalDate;

public record Contract(
        String number,
        LocalDate date,
        LocalDate paymentDate
) {}
