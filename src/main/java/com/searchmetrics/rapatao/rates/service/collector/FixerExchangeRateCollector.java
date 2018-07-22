package com.searchmetrics.rapatao.rates.service.collector;

import com.searchmetrics.rapatao.rates.RatesApp;
import com.searchmetrics.rapatao.rates.config.FixerConfig;
import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.service.ExchangeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class FixerExchangeRateCollector implements ExchangeRateCollector {

    private final FixerConfig fixerConfig;
    private final ExchangeService exchangeService;
    private final RestTemplate restTemplate;

    @Autowired
    public FixerExchangeRateCollector(FixerConfig fixerConfig, ExchangeService exchangeService) {
        this(fixerConfig, exchangeService, new RestTemplate());
    }

    @Scheduled(cron = "${fixer.cron}")
    @Override
    public void collect() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", RatesApp.getRuntimeAppName());

        final HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);

        final String uri = UriComponentsBuilder.fromUriString(fixerConfig.getEndpoint())
                .queryParam("access_key", fixerConfig.getApiKey())
                .queryParam("format", "1")
                .queryParam("symbols", fixerConfig.getCurrencies())
                .toUriString();

        final ResponseEntity<Exchange> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Exchange.class);

        exchangeService.save(exchange.getBody());
    }

}
