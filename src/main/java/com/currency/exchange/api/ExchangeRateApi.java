package com.currency.exchange.api;

import com.currency.exchange.api.representation.ExchangeRatesRepresentation;
import com.currency.exchange.api.representation.RateRepresentation;
import com.currency.exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExchangeRateApi {

  @Autowired private ExchangeRateService exchangeRateService;

  @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ExchangeRatesRepresentation> getExchangeRate() {
    return ResponseEntity.ok()
        .body(ExchangeRatesRepresentation.from(exchangeRateService.getAllExchangeRates()));
  }

  @GetMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RateRepresentation> getExchangeRate(
      @RequestParam String symbol,
      @RequestParam(required = false, defaultValue = "1") Double amount) {
    return ResponseEntity.ok()
        .body(RateRepresentation.from(exchangeRateService.convertRateByCurrency(symbol, amount)));
  }
}
