package com.searchmetrics.rapatao.rates.service;

import com.searchmetrics.rapatao.rates.domain.Exchange;
import com.searchmetrics.rapatao.rates.domain.ExchangeRate;
import com.searchmetrics.rapatao.rates.exception.ExchangeNotFoundException;
import com.searchmetrics.rapatao.rates.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;

    @Override
    public ExchangeRate getLastExchange(String from, String to) {
        final Optional<Exchange> lastExchange = exchangeRepository.findFirstByOrderByTimestampDesc();

        return ExchangeRate.build(lastExchange.orElseThrow(ExchangeNotFoundException::new), from, to, BigDecimal.ONE);
    }

    @Override
    public ExchangeRate convert(String from, String to, BigDecimal amount) {
        final Optional<Exchange> lastExchange = exchangeRepository.findFirstByOrderByTimestampDesc();

        return ExchangeRate.build(lastExchange.orElseThrow(ExchangeNotFoundException::new), from, to, amount);
    }

    @Override
    public List<ExchangeRate> getAllBetween(String from, String to, Instant start, Instant end) {
        final List<Exchange> byTimestampBetween = exchangeRepository.findByTimestampBetween(start, end);

        return byTimestampBetween.stream()
                .map(e -> ExchangeRate.build(e, from, to, BigDecimal.ONE))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

}
