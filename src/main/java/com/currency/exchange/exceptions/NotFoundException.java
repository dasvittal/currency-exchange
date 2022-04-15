package com.currency.exchange.exceptions;

public class NotFoundException extends CurrencyExchangeException{
    public NotFoundException(String msg, Exception exception) {
        super(msg, exception);
    }
}
