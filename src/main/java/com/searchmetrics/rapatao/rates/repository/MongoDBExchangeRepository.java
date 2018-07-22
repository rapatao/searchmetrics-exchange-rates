package com.searchmetrics.rapatao.rates.repository;

import com.searchmetrics.rapatao.rates.domain.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
interface MongoDBExchangeRepository extends ExchangeRepository, MongoRepository<Exchange, Instant> {

    @Override
    Optional<Exchange> findFirstByOrderByTimestampDesc();

    @Override
    List<Exchange> findByTimestampBetween(Instant start, Instant end);

}
