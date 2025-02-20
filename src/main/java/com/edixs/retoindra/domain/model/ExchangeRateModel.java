package com.edixs.retoindra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateModel {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private Double rate;
}