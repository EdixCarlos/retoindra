package com.edixs.retoindra.domain.usecase;

import com.edixs.retoindra.domain.api.IExchangeRateServicePort;
import com.edixs.retoindra.domain.model.ExchangeRateModel;
import com.edixs.retoindra.domain.spi.IExchangeRatePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ExchangeRateUseCase implements IExchangeRateServicePort {

    private final IExchangeRatePersistencePort exchangeRatePersistencePort;

    @Override
    public Mono<ExchangeRateModel> getExchangeRate(String sourceCurrency, String targetCurrency) {
        return exchangeRatePersistencePort.getExchangeRate(sourceCurrency, targetCurrency);
    }

    @Override
    public Mono<ExchangeRateModel> updateExchangeRate(ExchangeRateModel exchangeRateModel) {
        return exchangeRatePersistencePort.updateExchangeRate(exchangeRateModel);
    }

    @Override
    public Mono<Page<ExchangeRateModel>> getAllExchangeRates(Pageable pageable) {
        return exchangeRatePersistencePort.getAllExchangeRates(pageable);
    }
}