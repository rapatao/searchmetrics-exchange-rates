package com.searchmetrics.rapatao.rates.domain;

import com.searchmetrics.rapatao.rates.exception.ExchangeNotFoundException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * Entity that map a exchange rate for the given currency to another.
 */
@Builder
@Getter
public class ExchangeRate {

    /**
     * The timestamp for the exchange rate
     */
    private final Instant timestamp;

    /**
     * The origin currency code.
     */
    private final String from;

    /**
     * The destination currency code.
     */
    private final String to;

    /**
     * The calculated value
     */
    private final BigDecimal value;

    /**
     * Build an ExchangeRate using an Exchange, the currencies and the amount to calculate the value.
     *
     * @param exchange the exchange rate used to calculate.
     * @param from     the origin currency code.
     * @param to       the destination currency code.
     * @param amount   the amount of the origin currency that are used to calculate the amount in the destination currency.
     * @return An exchange rate from the given parameters.
     */
    public static ExchangeRate build(Exchange exchange, String from, String to, BigDecimal amount) {
        return ExchangeRate.builder()
                .timestamp(exchange.getTimestamp())
                .from(from)
                .to(to)
                .value(convert(exchange, from, to, amount))
                .build();
    }

    /**
     * Convert the amount using an Exchange rate, the currencies and the amount
     *
     * @param exchange the exchange rate used to calculate.
     * @param from     the origin currency code.
     * @param to       the destination currency code.
     * @param amount   the amount of the origin currency that are used to calculate the amount in the destination currency.
     * @return The calculated amount in the destination currency.
     */
    protected static BigDecimal convert(Exchange exchange, String from, String to, BigDecimal amount) {
        BigDecimal result;

        if (exchange.getBase().equals(from)) {
            final BigDecimal rate = getRateFromExchange(to, exchange);
            result = amount.multiply(rate);
        } else {
            if (exchange.getBase().equals(to)) {
                final BigDecimal rate = getRateFromExchange(from, exchange);
                result = amount.divide(rate, 2, RoundingMode.CEILING);
            } else {
                final BigDecimal fromRate = getRateFromExchange(from, exchange);
                final BigDecimal newAmount = amount.divide(fromRate, 2, RoundingMode.CEILING);

                result = newAmount.multiply(getRateFromExchange(to, exchange));
            }
        }

        return result.setScale(2, RoundingMode.CEILING);
    }

    private static BigDecimal getRateFromExchange(String currency, Exchange exchange) {
        final BigDecimal bigDecimal = exchange.getRates().get(currency);
        if (bigDecimal == null) {
            throw new ExchangeNotFoundException();
        }
        return bigDecimal;
    }

}
