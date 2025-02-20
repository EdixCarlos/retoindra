package com.edixs.retoindra.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateResponse {
    private Double amount;
    private Double convertedAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private Double rate;
}