package com.currency.exchange.service;

import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;

@Service
public class ExchangeRateService {

  @Value("exchange.currency.base")
  private String baseCurrency;

  private final ExchangeStorageService exchangeStorageService;

//  @Autowired
  public ExchangeRateService(ExchangeStorageService exchangeStorageService) {
    this.exchangeStorageService = exchangeStorageService;
  }

  public Set<ExchangeRate> getAllExchangeRates() {
    return exchangeStorageService.getRates();
  }

  public ExchangeRate convertRateByCurrency(final String symbol, double amount) {
    var symbols = symbol.split("/");
    if (symbols.length != 2) {
      throw new IllegalArgumentException("Invalid currency request!");
    }
    var rates = exchangeStorageService.getRates();

    ExchangeRate requestedCur;

    if (baseCurrency.equalsIgnoreCase(symbols[0])) {
      requestedCur = ExchangeRate.getBaseCurrencyRate(symbols[0]);
    } else {
      requestedCur = findRateBySymbol(rates, symbols[0]);
      requestedCur.incrementCount();
    }

    ExchangeRate result;

    if (requestedCur.getBaseCurrency().equalsIgnoreCase(symbols[1])) {
      result = requestedCur;
    } else {
      var baseCurrency = findRateBySymbol(rates, symbols[1]);
      baseCurrency.incrementCount();

      result =
          convertCurrencyByBaseCurrency(requestedCur.getCurrency(), baseCurrency.getCurrency());
    }
    result.calculateAndSetAmount(amount);

    return result;
  }

  private ExchangeRate convertCurrencyByBaseCurrency(Currency currency, Currency baseCurrency) {
    var calculatedValue = currency.getSpot().divide(baseCurrency.getSpot(), RoundingMode.HALF_UP);

    return ExchangeRate.builder()
        .currency(Currency.from(currency.getSymbol(), calculatedValue, currency.getDescription()))
        .localDate(LocalDate.now())
        .baseCurrency(baseCurrency.getSymbol())
        .build();
  }

  private ExchangeRate findRateBySymbol(Set<ExchangeRate> rates, String symbol) {
    return rates.stream()
        .filter(currency -> currency.getCurrency().getSymbol().equalsIgnoreCase(symbol))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid currency request!"));
  }
}
