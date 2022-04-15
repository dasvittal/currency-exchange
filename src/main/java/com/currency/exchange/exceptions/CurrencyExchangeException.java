package com.currency.exchange.exceptions;

public class CurrencyExchangeException extends RuntimeException {
    public CurrencyExchangeException(String msg) {
        super(msg);
    }
    public CurrencyExchangeException(String msg, Exception exception) {
        super(msg, exception);
    }
}
