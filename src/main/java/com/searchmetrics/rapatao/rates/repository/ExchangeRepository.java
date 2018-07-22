package com.searchmetrics.rapatao.rates.repository;

import com.searchmetrics.rapatao.rates.domain.Exchange;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Specify the default methods that must be provided by the repository implementation.
 */
@NoRepositoryBean
public interface ExchangeRepository {

    /**
     * Finds the last exchange rate.
     *
     * @return the exchange rate.
     */
    Optional<Exchange> findFirstByOrderByTimestampDesc();


    /**
     * Finds all exchange rates between the provided interval.
     *
     * @param start start date.
     * @param end   end date.
     * @return a list with found exchanges.
     */
    List<Exchange> findByTimestampBetween(Instant start, Instant end);


    /**
     * Create or update the exchange rate.
     *
     * @param exchange the rate definition.
     * @return the saved exchange.
     */
    Exchange save(Exchange exchange);

}
