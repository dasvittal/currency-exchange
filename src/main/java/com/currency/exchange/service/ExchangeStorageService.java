package com.currency.exchange.service;

import com.currency.exchange.model.ExchangeRate;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
@Component
@Scope("singleton")
public class ExchangeStorageService {
  private Set<ExchangeRate> rates;
  private LocalDateTime lastUpdateTime;

  public void updateExchangeRates(Set<ExchangeRate> rates) {
    if (!rates.isEmpty()) {
      this.rates = rates;
      this.lastUpdateTime = LocalDateTime.now();
    }
  }

  public Set<ExchangeRate> getRates() {
    return Objects.requireNonNullElse(rates,Collections.emptySet());
  }
}
