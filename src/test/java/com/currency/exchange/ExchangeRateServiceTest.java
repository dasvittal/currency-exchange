package com.currency.exchange;

import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;
import com.currency.exchange.service.ExchangeRateService;
import com.currency.exchange.service.ExchangeStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {"exchange.currency.base=EUR"})
public class ExchangeRateServiceTest {
  private final ExchangeStorageService storageService = mock(ExchangeStorageService.class);
  private final ExchangeRateService exchangeRateService = new ExchangeRateService(storageService);

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(exchangeRateService, "baseCurrency", "EUR");
  }

  @Test
  void shouldReturnAllSupportedCurrencies() {
    when(storageService.getRates()).thenReturn(getExchangeRates());

    var rates = exchangeRateService.getAllExchangeRates();

    assertThat(rates).hasSize(4);
  }

  @ParameterizedTest
  @MethodSource("currencyConversionRates")
  void shouldReturnConvertedExchangeRate(String symbols, double amount, double expectedAMount) {
    when(storageService.getRates()).thenReturn(getExchangeRates());

    var rate = exchangeRateService.convertRateByCurrency(symbols, amount);

    assertThat(rate.getAmount().doubleValue()).isEqualTo(expectedAMount);
  }

  private static Stream<Arguments> currencyConversionRates() {
    return Stream.of(
        Arguments.of("EUR/EUR", 1, 1),
        Arguments.of("AUD/EUR", 1, 1.4612),
        Arguments.of("BGN/BRL", 1, 0.3818),
        Arguments.of("CAD/EUR", 3, 4.0989),
        Arguments.of("BRL/AUD", 5, 17.5285));
  }

  private Set<ExchangeRate> getExchangeRates() {
    var e1 =
        ExchangeRate.builder()
            .currency(Currency.from("AUD", BigDecimal.valueOf(1.4612), ""))
            .baseCurrency("EUR")
            .build();
    var e2 =
        ExchangeRate.builder()
            .currency(Currency.from("BGN", BigDecimal.valueOf(1.9558), ""))
            .baseCurrency("EUR")
            .build();
    var e3 =
        ExchangeRate.builder()
            .currency(Currency.from("BRL", BigDecimal.valueOf(5.1226), ""))
            .baseCurrency("EUR")
            .build();
    var e4 =
        ExchangeRate.builder()
            .currency(Currency.from("CAD", BigDecimal.valueOf(1.3663), ""))
            .baseCurrency("EUR")
            .build();

    return Set.of(e1, e2, e3, e4);
  }
}
