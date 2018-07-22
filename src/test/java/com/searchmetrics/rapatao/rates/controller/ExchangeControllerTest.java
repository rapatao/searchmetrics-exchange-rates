package com.searchmetrics.rapatao.rates.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.domain.ExchangeRate;
import com.searchmetrics.rapatao.rates.domain.ExchangeRateTest;
import com.searchmetrics.rapatao.rates.service.ExchangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldFailToGetExchangeRateWhenDoesNotExistRates() throws Exception {
        mvc.perform(get("/rates")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200ForTheHistoricalSearch() throws Exception {
        mvc.perform(get("/rates/historical")
                .param("start", "2018-07-01T00:00:00Z")
                .param("end", "2018-07-30T00:00:00Z")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnLastExchangeRate() throws Exception {
        final Exchange exchange = ExchangeRateTest.createExchange();

        exchangeService.save(exchange);

        final String expected = objectMapper.writeValueAsString(ExchangeRate.build(exchange, "EUR", "USD", BigDecimal.ONE));

        mvc.perform(get("/rates")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LINK))
                .andExpect(content().string(expected));
    }

}