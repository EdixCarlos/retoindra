package com.edixs.retoindra.application.handler;

import com.edixs.retoindra.domain.model.ExchangeRateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IExchangeRateHandler {
    Mono<ExchangeRateModel> getExchangeRate(String sourceCurrency, String targetCurrency);
    Mono<ExchangeRateModel> updateExchangeRate(ExchangeRateModel exchangeRateModel);
    Mono<Page<ExchangeRateModel>> getAllExchangeRates(Pageable pageable);
}