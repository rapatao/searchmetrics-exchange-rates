package com.searchmetrics.rapatao.rates.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * Entity that maps an exchange rate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Exchange {

    /**
     * The timestamp.
     */
    @Id
    private Instant timestamp;

    /**
     * The base currency code.
     */
    private String base;

    /**
     * A map of currency and their rates from the base currency.
     */
    private Map<String, BigDecimal> rates;

}
