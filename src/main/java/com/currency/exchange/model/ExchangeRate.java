package com.currency.exchange.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ExchangeRate {
  private Currency currency;
  private BigDecimal amount;
  private String baseCurrency;
  private String pageRef;
  private LocalDate localDate;

  @Setter(AccessLevel.PRIVATE)
  private long count;

  private ExchangeRate(
      Currency currency,
      BigDecimal amount,
      String baseCurrency,
      String pageRef,
      LocalDate localDate,
      long count) {
    this.currency = currency;
    this.setAmount(BigDecimal.ONE);
    this.baseCurrency = baseCurrency;
    this.pageRef = pageRef;
    this.localDate = LocalDate.now();
    this.count = 0;
  }

  private void setAmount(BigDecimal amount) {
    this.amount = this.currency.getSpot().multiply(amount);
  }

  public void incrementCount() {
    this.count++;
  }

  public void calculateAndSetAmount(double value) {
    setAmount(BigDecimal.valueOf(value));
  }

  public static ExchangeRate getBaseCurrencyRate(String symbol) {
    return ExchangeRate.builder()
        .currency(Currency.from(symbol, BigDecimal.ONE, ""))
        .baseCurrency(symbol)
        .build();
  }
}
