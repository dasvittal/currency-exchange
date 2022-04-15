package com.currency.exchange;

import com.currency.exchange.ecb.ECBRateExtractor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ECBExtractorTest {

  @Test
  void shouldFetchExchangeRates() {
    var exchangeRates = ECBRateExtractor.fetchECBRatesOfEUR();

    assertThat(exchangeRates).hasSizeGreaterThanOrEqualTo(31);
  }
}
