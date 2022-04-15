package com.currency.exchange.exceptions;

import com.currency.exchange.api.representation.CurrencyExchangeErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CurrencyExchangeExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {IllegalArgumentException.class, NotFoundException.class})
  protected ResponseEntity<CurrencyExchangeErrorResponse> handleBadRequest(
      RuntimeException ex, WebRequest request) {
    return ResponseEntity.badRequest()
        .body(CurrencyExchangeErrorResponse.from(ex.getMessage(), request.getContextPath()));
  }

  @ExceptionHandler(CurrencyExchangeException.class)
  protected ResponseEntity<CurrencyExchangeErrorResponse> handleInternalServerError(
      RuntimeException ex, WebRequest request) {
    return ResponseEntity.internalServerError()
        .body(CurrencyExchangeErrorResponse.from(ex.getMessage(), request.getContextPath()));
  }
}
