package com.edixs.retoindra.infrastructure.configuration;

import com.edixs.retoindra.domain.api.IExchangeRateServicePort;
import com.edixs.retoindra.domain.spi.IExchangeRatePersistencePort;
import com.edixs.retoindra.infrastructure.out.jpa.adapter.ExchangeRateJpaAdapter;
import com.edixs.retoindra.infrastructure.out.jpa.repository.IExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeRateBeanConfigurationTest {

    @Mock
    private IExchangeRateRepository exchangeRateRepository;

    private ExchangeRateBeanConfiguration exchangeRateBeanConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeRateBeanConfiguration = new ExchangeRateBeanConfiguration(exchangeRateRepository);
    }

    @Test
    void exchangeRatePersistencePort_shouldReturnNonNullBean() {
        when(exchangeRateRepository.findAll()).thenReturn(new ArrayList<>());
        IExchangeRatePersistencePort persistencePort = exchangeRateBeanConfiguration.exchangeRatePersistencePort();
        assertNotNull(persistencePort);
    }

    @Test
    void exchangeRateServicePort_shouldReturnNonNullBean() {
        when(exchangeRateRepository.findAll()).thenReturn(new ArrayList<>());
        IExchangeRateServicePort servicePort = exchangeRateBeanConfiguration.exchangeRateServicePort();
        assertNotNull(servicePort);
    }

    @Test
    void exchangeRatePersistencePort_shouldHandleEmptyRepository() {
        when(exchangeRateRepository.findAll()).thenReturn(new ArrayList<>());
        IExchangeRatePersistencePort persistencePort = exchangeRateBeanConfiguration.exchangeRatePersistencePort();
        assertNotNull(persistencePort);
    }

    @Test
    void exchangeRateServicePort_shouldHandleEmptyRepository() {
        when(exchangeRateRepository.findAll()).thenReturn(new ArrayList<>());
        IExchangeRateServicePort servicePort = exchangeRateBeanConfiguration.exchangeRateServicePort();
        assertNotNull(servicePort);
    }
}