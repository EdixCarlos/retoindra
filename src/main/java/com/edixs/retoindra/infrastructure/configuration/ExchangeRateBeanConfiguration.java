package com.edixs.retoindra.infrastructure.configuration;

import com.edixs.retoindra.domain.api.IExchangeRateServicePort;
import com.edixs.retoindra.domain.spi.IExchangeRatePersistencePort;
import com.edixs.retoindra.domain.usecase.ExchangeRateUseCase;
import com.edixs.retoindra.infrastructure.out.jpa.adapter.ExchangeRateJpaAdapter;
import com.edixs.retoindra.infrastructure.out.jpa.repository.IExchangeRateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeRateBeanConfiguration {

    private final IExchangeRateRepository exchangeRateRepository;

    public ExchangeRateBeanConfiguration(IExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Bean
    public IExchangeRatePersistencePort exchangeRatePersistencePort() {
        return new ExchangeRateJpaAdapter(exchangeRateRepository);
    }

    @Bean
    public IExchangeRateServicePort exchangeRateServicePort() {
        return new ExchangeRateUseCase(exchangeRatePersistencePort());
    }
}
