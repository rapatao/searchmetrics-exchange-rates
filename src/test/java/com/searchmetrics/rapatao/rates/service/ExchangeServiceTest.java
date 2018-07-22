package com.searchmetrics.rapatao.rates.service;

import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.domain.ExchangeRate;
import com.searchmetrics.rapatao.rates.domain.ExchangeRateTest;
import com.searchmetrics.rapatao.rates.exception.ExchangeNotFoundException;
import com.searchmetrics.rapatao.rates.repository.ExchangeRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExchangeServiceTest {

    private final Exchange baseExchange = ExchangeRateTest.createExchange();

    private ExchangeService exchangeService;
    private ExchangeRepository exchangeRepository;

    @Before
    public void setUp() {
        exchangeRepository = mock(ExchangeRepository.class);
        exchangeService = new ExchangeServiceImpl(exchangeRepository);
    }

    @Test(expected = ExchangeNotFoundException.class)
    public void shouldFailWhenDoesNotExistExchange() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.empty());

        exchangeService.getLastExchange("EUR", "USD");

        fail("Must fail due to a missing exchange rate");
    }

    @Test
    public void shouldGetLastExchangeRate() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.of(baseExchange));

        final ExchangeRate lastExchange = exchangeService.getLastExchange("EUR", "USD");

        assertThat(lastExchange.getValue(), comparesEqualTo(BigDecimal.valueOf(1.17)));
    }

    @Test
    public void shouldGetLastExchangeRateFromEURtoUSD() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.of(baseExchange));

        final ExchangeRate lastExchange = exchangeService.getLastExchange("EUR", "BRL");

        assertThat(lastExchange.getValue(), comparesEqualTo(BigDecimal.valueOf(4.42)));
    }

    @Test
    public void shouldGetLastExchangeRateFromBRLtoUSD() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.of(baseExchange));

        final ExchangeRate lastExchange = exchangeService.getLastExchange("BRL", "USD");

        assertThat(lastExchange.getValue(), comparesEqualTo(BigDecimal.valueOf(0.27)));
    }

    @Test(expected = ExchangeNotFoundException.class)
    public void shouldFailWhenRateDoesNotExist() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.empty());

        exchangeService.convert("EUR", "USD", BigDecimal.ONE);

        fail("The conversion must fail due to the missing conversion rate");
    }

    @Test
    public void shouldConvertEURtoUSD() {
        when(exchangeRepository.findFirstByOrderByTimestampDesc()).thenReturn(Optional.of(baseExchange));

        final ExchangeRate calculate = exchangeService.convert("EUR", "USD", BigDecimal.ONE);

        assertThat(calculate.getValue(), comparesEqualTo(BigDecimal.valueOf(1.17)));
    }

    @Test
    public void shouldSaveNewExchange() {
        exchangeService.save(ExchangeRateTest.createExchange());

        verify(exchangeRepository, times(1)).save(any());
    }


}