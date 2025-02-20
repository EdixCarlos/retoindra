package com.edixs.retoindra.application.dto.request;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Data
@Builder
public class ApplyExchangeRateRequest {
    @Schema(example = "100.0", description = "Amount must be greater than 0")
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @Schema(example = "USD", description = "Source currency must be 3 uppercase letters")
    @NotNull(message = "Source currency cannot be null")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Source currency must be 3 uppercase letters")
    private String sourceCurrency;

    @Schema(example = "EUR", description = "Target currency must be 3 uppercase letters")
    @NotNull(message = "Target currency cannot be null")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Target currency must be 3 uppercase letters")
    private String targetCurrency;
}