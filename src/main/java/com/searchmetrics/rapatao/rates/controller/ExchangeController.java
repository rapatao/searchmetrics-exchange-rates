package com.searchmetrics.rapatao.rates.controller;

import com.searchmetrics.rapatao.rates.domain.ExchangeRate;
import com.searchmetrics.rapatao.rates.service.ExchangeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Declares the endpoints that returns the latest exchange rate and the historical rates for the given period.
 */
@Api(tags = "rates", description = "Provides the endpoints to collect the exchanges rates")
@Controller
@RequestMapping("/rates")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * Endpoint that get the latest exchange rate from one currency to another.
     *
     * @param from origin currency code.
     * @param to   destination currency code.
     * @return exchange rate from one currency to another
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<ExchangeRate> last(@RequestParam(value = "from", defaultValue = "EUR") String from,
                                             @RequestParam(value = "to", defaultValue = "USD") String to) {

        final ExchangeRate lastExchange = exchangeService.getLastExchange(from, to);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LINK, linkTo(methodOn(ExchangeController.class).last(from, to)).withSelfRel().toString());

        return new ResponseEntity<>(lastExchange, httpHeaders, HttpStatus.OK);
    }

    /**
     * Endpoint that get the historical exchange rate from one currency to another during the given period.
     *
     * @param from  the origin currency code.
     * @param to    then destination currency code.
     * @param start the start date to be used as filter.
     * @param end   the end date to be used as filter.
     * @return the list of the exchange rates.
     */
    @GetMapping("/historical")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExchangeRate>> between(@RequestParam(value = "from", defaultValue = "EUR") String from,
                                                      @RequestParam(value = "to", defaultValue = "USD") String to,
                                                      @RequestParam(value = "start") Instant start,
                                                      @RequestParam(value = "end") Instant end) {

        final List<ExchangeRate> allBetween = exchangeService.getAllBetween(from, to, start, end);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LINK, linkTo(methodOn(ExchangeController.class).between(from, to, start, end)).toString());

        return new ResponseEntity<>(allBetween, httpHeaders, HttpStatus.OK);
    }

}
