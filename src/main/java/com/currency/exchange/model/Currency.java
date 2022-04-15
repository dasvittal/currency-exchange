package com.currency.exchange.model;

import lombok.Getter;
import lombok.ToString;
import org.jsoup.internal.StringUtil;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Currency {
  private String symbol;
  private final String description;
  private BigDecimal spot;

  private Currency(String symbol, BigDecimal spot,  String desc) {
    setSymbol(symbol);
    setSpot(spot);
    this.description = desc;
  }

  private void setSpot(BigDecimal aSpot) {
    if (Objects.isNull(aSpot)) {
      throw new IllegalArgumentException("Currency spot can't be null!");
    }
    this.spot = aSpot;
  }

  private void setSymbol(String aSymbol) {
    if (StringUtil.isBlank(aSymbol) || aSymbol.length() > 3) {
      throw new IllegalArgumentException("Currency symbol can't be blank!");
    }
    this.symbol = aSymbol;
  }

  public static Currency from(String symbol, BigDecimal spot ,String desc) {
    return new Currency(symbol, spot, desc);
  }
}
