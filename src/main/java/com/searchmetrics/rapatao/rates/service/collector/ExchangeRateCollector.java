package com.searchmetrics.rapatao.rates.service.collector;

/**
 * Specify the default methods that must be provided by the rate collector implementation.
 */
interface ExchangeRateCollector {

    /**
     * Execute the job that collect information from the provider and persist into the local database.
     */
    void collect();

}
