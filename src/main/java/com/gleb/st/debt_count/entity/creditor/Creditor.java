package com.gleb.st.debt_count.entity.creditor;

public record Creditor (
        int id,
        String name,
        String paymentAccount,
        String unp,
        String address
) {}
