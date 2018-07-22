package com.searchmetrics.rapatao.rates.service;

import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.domain.ExchangeRate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Specify the default methods that must be provided by the exchange rate service implementation.
 */
public interface ExchangeService {

    /**
     * Get the rate from one currency to another.
     *
     * @param from the origin currency code.
     * @param to   the destination currency code.
     * @return the amount of the value 1 from the first currency to another currency.
     */
    ExchangeRate getLastExchange(String from, String to);

    /**
     * Convert the amount of one currency to another.
     *
     * @param from   the origin currency code.
     * @param to     the destination currency code.
     * @param amount the amount.
     * @return the amount converted into the destination currency.
     */
    ExchangeRate convert(String from, String to, BigDecimal amount);

    /**
     * Get all exchange rates for the currencies during the provided period.
     *
     * @param from  the origin currency code.
     * @param to    the destination currency code.
     * @param start the start date to be used as filter.
     * @param end   the end date to be used as filter.
     * @return the list of the rates.
     */
    List<ExchangeRate> getAllBetween(String from, String to, Instant start, Instant end);

    /**
     * Create or update the exchange rate.
     *
     * @param exchange the rate definition.
     * @return the saved exchange.
     */
    void save(Exchange exchange);
}
