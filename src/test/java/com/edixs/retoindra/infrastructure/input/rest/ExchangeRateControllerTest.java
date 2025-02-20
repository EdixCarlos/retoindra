package com.edixs.retoindra.infrastructure.input.rest;

import com.edixs.retoindra.application.dto.request.ApplyExchangeRateRequest;
import com.edixs.retoindra.application.dto.request.UpdateExchangeRateRequest;
import com.edixs.retoindra.application.dto.response.ExchangeRateResponse;
import com.edixs.retoindra.application.handler.IExchangeRateHandler;
import com.edixs.retoindra.domain.model.ExchangeRateModel;
import com.edixs.retoindra.infrastructure.common.PaginationUtil;
import com.edixs.retoindra.infrastructure.exception.InvalidExchangeRateException;
import com.edixs.retoindra.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExchangeRateControllerTest {

    @Mock
    private IExchangeRateHandler exchangeRateHandler;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void applyExchangeRate_success() {
        ApplyExchangeRateRequest request = ApplyExchangeRateRequest.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .amount(100.0)
                .build();
        ExchangeRateModel exchangeRateModel = ExchangeRateModel.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(0.85)
                .build();
        when(exchangeRateHandler.getExchangeRate("USD", "EUR")).thenReturn(Mono.just(exchangeRateModel));

        Mono<ExchangeRateResponse> response = exchangeRateController.applyExchangeRate(request);

        StepVerifier.create(response)
                .expectNextMatches(exchangeRateResponse -> exchangeRateResponse.getConvertedAmount() == 85.0)
                .verifyComplete();
    }

    @Test
    void applyExchangeRate_notFound() {
        ApplyExchangeRateRequest request = ApplyExchangeRateRequest.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .amount(100.0)
                .build();
        when(exchangeRateHandler.getExchangeRate("USD", "EUR")).thenReturn(Mono.empty());

        Mono<ExchangeRateResponse> response = exchangeRateController.applyExchangeRate(request);

        StepVerifier.create(response)
                .expectError(NoDataFoundException.class)
                .verify();
    }

    @Test
    void getExchangeRate_success() {
        ExchangeRateModel exchangeRateModel = ExchangeRateModel.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(0.85)
                .build();
        when(exchangeRateHandler.getExchangeRate("USD", "EUR")).thenReturn(Mono.just(exchangeRateModel));

        Mono<ExchangeRateModel> response = exchangeRateController.getExchangeRate("USD", "EUR");

        StepVerifier.create(response)
                .expectNext(exchangeRateModel)
                .verifyComplete();
    }

    @Test
    void getExchangeRate_notFound() {
        when(exchangeRateHandler.getExchangeRate("USD", "EUR")).thenReturn(Mono.empty());

        Mono<ExchangeRateModel> response = exchangeRateController.getExchangeRate("USD", "EUR");

        StepVerifier.create(response)
                .expectError(NoDataFoundException.class)
                .verify();
    }

    @Test
    void updateExchangeRate_success() {
        UpdateExchangeRateRequest request = UpdateExchangeRateRequest.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(0.85)
                .build();
        ExchangeRateModel exchangeRateModel = ExchangeRateModel.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(0.85)
                .build();
        when(exchangeRateHandler.updateExchangeRate(any(ExchangeRateModel.class))).thenReturn(Mono.just(exchangeRateModel));

        Mono<ExchangeRateModel> response = exchangeRateController.updateExchangeRate(request);

        StepVerifier.create(response)
                .expectNext(exchangeRateModel)
                .verifyComplete();
    }

    @Test
    void updateExchangeRate_invalidRate() {
        UpdateExchangeRateRequest request = UpdateExchangeRateRequest.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(-0.85)
                .build();

        StepVerifier.create(Mono.defer(() -> exchangeRateController.updateExchangeRate(request)))
                .expectErrorMatches(throwable -> throwable instanceof InvalidExchangeRateException &&
                        throwable.getMessage().equals("Exchange rate must be greater than zero"))
                .verify();
    }

    @Test
    void getAllExchangeRates_success() {
        Pageable pageable = PaginationUtil.createPageable(0, 4, "rate,asc");
        Page<ExchangeRateModel> page = new PageImpl<>(List.of(ExchangeRateModel.builder()
                .sourceCurrency("USD")
                .targetCurrency("EUR")
                .rate(0.85)
                .build()));
        when(exchangeRateHandler.getAllExchangeRates(pageable)).thenReturn(Mono.just(page));

        Mono<Page<ExchangeRateModel>> response = exchangeRateController.getAllExchangeRates(0, 4, "rate,asc", null);

        StepVerifier.create(response)
                .expectNext(page)
                .verifyComplete();
    }

    @Test
    void getAllExchangeRates_notFound() {
        Pageable pageable = PaginationUtil.createPageable(0, 4, "rate,asc");
        when(exchangeRateHandler.getAllExchangeRates(pageable)).thenReturn(Mono.just(Page.empty()));

        Mono<Page<ExchangeRateModel>> response = exchangeRateController.getAllExchangeRates(0, 4, "rate,asc", null);

        StepVerifier.create(response)
                .expectError(NoDataFoundException.class)
                .verify();
    }
}