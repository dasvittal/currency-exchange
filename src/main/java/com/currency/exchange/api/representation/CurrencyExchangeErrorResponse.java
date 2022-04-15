package com.currency.exchange.api.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyExchangeErrorResponse {
  private String error;
  private String path;

  public static CurrencyExchangeErrorResponse from(String err,String path) {
    return new CurrencyExchangeErrorResponse(err, path);
  }
}
