package com.currency.exchange.api.representation;

import com.currency.exchange.model.ExchangeRate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateRepresentation {
  private String currency;
  private String baseCurrency;
  private double rate;
  private double amount;
  private String chart;
  private long count;

  public static RateRepresentation from(ExchangeRate exchangeRate) {
    return RateRepresentation.builder()
        .currency(exchangeRate.getCurrency().getSymbol())
        .baseCurrency(exchangeRate.getBaseCurrency())
        .rate(exchangeRate.getCurrency().getSpot().doubleValue())
        .amount(exchangeRate.getAmount().doubleValue())
        .chart(exchangeRate.getPageRef())
        .count(exchangeRate.getCount())
        .build();
  }
}
