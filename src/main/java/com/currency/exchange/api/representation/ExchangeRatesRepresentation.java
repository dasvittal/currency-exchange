package com.currency.exchange.api.representation;

import com.currency.exchange.model.ExchangeRate;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class ExchangeRatesRepresentation {
  private LocalDateTime date;
  private List<RateRepresentation> rates;

  public static ExchangeRatesRepresentation from(
      Set<ExchangeRate> exchangeRates) {
    return ExchangeRatesRepresentation.builder()
        .date(LocalDateTime.now())
        .rates(exchangeRates.stream().map(RateRepresentation::from).collect(Collectors.toList()))
        .build();
  }
}
