package com.searchmetrics.rapatao.rates.domain;

import com.searchmetrics.rapatao.rates.exception.ExchangeNotFoundException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ExchangeRateTest {

    private final Exchange exchange;

    public ExchangeRateTest() {
        exchange = createExchange();
    }

    public static Exchange createExchange() {
        final Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("USD", BigDecimal.valueOf(1.17));
        rates.put("BRL", BigDecimal.valueOf(4.42));

        return Exchange.builder()
                .base("EUR")
                .timestamp(Instant.now())
                .rates(rates)
                .build();
    }

    @Test
    public void shouldConvertEURtoUSD() {
        final BigDecimal calculate = ExchangeRate.build(exchange, "EUR", "USD", BigDecimal.ONE).getValue();

        assertThat(calculate, comparesEqualTo(BigDecimal.valueOf(1.17)));
    }

    @Test
    public void shouldConvertBRLtoUSD() {
        final BigDecimal calculate = ExchangeRate.build(exchange, "BRL", "USD", BigDecimal.valueOf(4)).getValue();

        assertThat(calculate, comparesEqualTo(BigDecimal.valueOf(1.07)));
    }

    @Test
    public void shouldConvertBRLtoEUR() {
        final BigDecimal calculate = ExchangeRate.build(exchange, "BRL", "EUR", BigDecimal.valueOf(4.42)).getValue();

        assertThat(calculate, comparesEqualTo(BigDecimal.valueOf(1)));
    }

    @Test
    public void shouldConvertUSDtoEUR() {
        final BigDecimal calculate = ExchangeRate.build(exchange, "USD", "EUR", BigDecimal.valueOf(4)).getValue();

        assertThat(calculate, comparesEqualTo(BigDecimal.valueOf(3.42)));
    }

    @Test(expected = ExchangeNotFoundException.class)
    public void shouldFailWhenConvertToUnexpectedCurrency() {
        ExchangeRate.build(exchange, "EUR", "GBP", BigDecimal.valueOf(4));

        fail("The conversion must fail due to the missing conversion rate");
    }

}