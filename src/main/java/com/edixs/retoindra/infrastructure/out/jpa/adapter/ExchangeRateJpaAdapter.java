package com.edixs.retoindra.infrastructure.out.jpa.adapter;

import com.edixs.retoindra.domain.model.ExchangeRateModel;
import com.edixs.retoindra.domain.spi.IExchangeRatePersistencePort;
import com.edixs.retoindra.infrastructure.out.jpa.entity.ExchangeRateEntity;
import com.edixs.retoindra.infrastructure.out.jpa.repository.IExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExchangeRateJpaAdapter implements IExchangeRatePersistencePort {

    private final IExchangeRateRepository exchangeRateRepository;

    @Override
    public Mono<ExchangeRateModel> getExchangeRate(String sourceCurrency, String targetCurrency) {
        return Mono.justOrEmpty(exchangeRateRepository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency)
                .map(this::toModel));
    }

    @Override
    public Mono<ExchangeRateModel> updateExchangeRate(ExchangeRateModel exchangeRateModel) {
        ExchangeRateEntity entity = toEntity(exchangeRateModel);
        ExchangeRateEntity savedEntity = exchangeRateRepository.save(entity);
        return Mono.just(toModel(savedEntity));
    }

    @Override
    public Mono<Page<ExchangeRateModel>> getAllExchangeRates(Pageable pageable) {
        return Mono.just(exchangeRateRepository.findAll(pageable)
                .map(this::toModel));
    }

    private ExchangeRateModel toModel(ExchangeRateEntity entity) {
        return ExchangeRateModel.builder()
                .id(entity.getId())
                .sourceCurrency(entity.getSourceCurrency())
                .targetCurrency(entity.getTargetCurrency())
                .rate(entity.getRate())
                .build();
    }

    private ExchangeRateEntity toEntity(ExchangeRateModel model) {
        ExchangeRateEntity entity = new ExchangeRateEntity();
        entity.setId(model.getId());
        entity.setSourceCurrency(model.getSourceCurrency());
        entity.setTargetCurrency(model.getTargetCurrency());
        entity.setRate(model.getRate());
        return entity;
    }
}