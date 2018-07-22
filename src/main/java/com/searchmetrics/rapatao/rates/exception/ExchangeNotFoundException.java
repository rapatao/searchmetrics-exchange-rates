package com.searchmetrics.rapatao.rates.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when attempts to get and exchange rate that does not exist.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExchangeNotFoundException extends RuntimeException {
}
