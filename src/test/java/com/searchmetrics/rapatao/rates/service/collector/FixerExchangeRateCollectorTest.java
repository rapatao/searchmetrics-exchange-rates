package com.searchmetrics.rapatao.rates.service.collector;

import com.searchmetrics.rapatao.rates.config.FixerConfig;
import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.domain.ExchangeRateTest;
import com.searchmetrics.rapatao.rates.service.ExchangeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FixerExchangeRateCollectorTest {

    private final FixerConfig fixerConfig = new FixerConfig(
            "http://data.fixer.io/api/latest", "123", "BRL,USD,EUR", null);

    private RestTemplate restTemplate;
    private ExchangeService exchangeService;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        exchangeService = mock(ExchangeService.class);
    }

    @Test
    public void shouldRetrieve() {
        final ExchangeRateCollector exchangeRateCollector = spy(new FixerExchangeRateCollector(fixerConfig, exchangeService, restTemplate));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Exchange.class)))
                .thenReturn(ResponseEntity.ok(ExchangeRateTest.createExchange()));

        exchangeRateCollector.collect();

        verify(exchangeService, times(1)).save(any());
    }


}