package com.seerbit.seerbit.assessment.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class Transaction {
    private BigDecimal amount;
    private Instant timestamp;
}
