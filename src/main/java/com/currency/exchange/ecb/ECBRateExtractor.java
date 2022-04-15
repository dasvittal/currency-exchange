package com.currency.exchange.ecb;

import com.currency.exchange.exceptions.CurrencyExchangeException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ECBRateExtractor {
  private static final String BASE_CURRENCY = "EUR";
  private static final String ECB_URL =
      "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/";

  public static Set<ExchangeRate> fetchECBRatesOfEUR() {
    log.info("Initiating exchange rates update");
    Document doc;
    try {
      doc = Jsoup.connect(ECB_URL + "index.en.html").get();
      Elements rateRows = doc.select("table.forextable > tbody > tr");
      Set<ExchangeRate> exchangeRates = new HashSet<>();
      if (rateRows.size() > 0) {
        rateRows.forEach(
            element -> {
              var rate = extractRateFromRow(element);
              exchangeRates.add(rate);
            });
      }
      log.info("Exchange Rates updated");
      return exchangeRates;
    } catch (IOException e) {
      log.error("Failed to fetch exchange rates.", e);
      throw new CurrencyExchangeException("Failed to parse Exchange rate page.", e);
    }
  }

  private static ExchangeRate extractRateFromRow(Element elementRow) {
    Elements columns = elementRow.select("td");
    String cur = null;
    String desc = "";
    String spot = "";
    String url = "";

    if (columns.size() > 0) {
      cur = columns.get(0).select("a").text();
      desc = columns.get(1).select("a").text();
      spot = columns.get(2).select("span").text().trim();
      url = columns.get(1).select("a").attr("href");
    }
    var spotVal = BigDecimal.valueOf(Double.parseDouble(spot));
    var currency = Currency.from(cur, spotVal, desc);
    return ExchangeRate.builder()
        .currency(currency)
        .pageRef(ECB_URL + url)
        .baseCurrency(BASE_CURRENCY)
        .build();
  }
}
