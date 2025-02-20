package com.edixs.retoindra.application.handler.impl;

import com.edixs.retoindra.application.handler.IExchangeRateHandler;
import com.edixs.retoindra.domain.api.IExchangeRateServicePort;
import com.edixs.retoindra.domain.model.ExchangeRateModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateHandler implements IExchangeRateHandler {

    private final IExchangeRateServicePort exchangeRateServicePort;

    @Override
    public Mono<ExchangeRateModel> getExchangeRate(String sourceCurrency, String targetCurrency) {
        return exchangeRateServicePort.getExchangeRate(sourceCurrency, targetCurrency);
    }

    @Override
    public Mono<ExchangeRateModel> updateExchangeRate(ExchangeRateModel exchangeRateModel) {
        return exchangeRateServicePort.updateExchangeRate(exchangeRateModel);
    }

    @Override
    public Mono<Page<ExchangeRateModel>> getAllExchangeRates(Pageable pageable) {
        return exchangeRateServicePort.getAllExchangeRates(pageable);
    }
}