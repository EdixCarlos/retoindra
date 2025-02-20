package com.edixs.retoindra.infrastructure.input.rest;

import com.edixs.retoindra.application.dto.request.ApplyExchangeRateRequest;
import com.edixs.retoindra.application.dto.request.UpdateExchangeRateRequest;
import com.edixs.retoindra.application.dto.response.ExchangeRateResponse;
import com.edixs.retoindra.application.handler.IExchangeRateHandler;
import com.edixs.retoindra.domain.model.ExchangeRateModel;
import com.edixs.retoindra.infrastructure.common.PaginationUtil;
import com.edixs.retoindra.infrastructure.exception.InvalidExchangeRateException;
import com.edixs.retoindra.infrastructure.exception.NoDataFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange-rate")
@Tag(name = "Exchange Rate", description = "Endpoints for managing exchange rates")
@Slf4j
public class ExchangeRateController {

    private final IExchangeRateHandler exchangeRateHandler;

    @PostMapping("/apply")
    @Operation(
            summary = "Apply exchange rate",
            description = "Applies the exchange rate to the given amount",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Exchange rate applied successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExchangeRateResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"amount\": 100, \"convertedAmount\": 85, \"sourceCurrency\": \"USD\", \"targetCurrency\": \"EUR\", \"rate\": 0.85}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Exchange rate not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"error\": \"Exchange rate not found\"}"
                                    )
                            )
                    )
            }
    )
    public Mono<ExchangeRateResponse> applyExchangeRate(@RequestBody ApplyExchangeRateRequest request) {
        log.info("Applying exchange rate for amount: {}, sourceCurrency: {}, targetCurrency: {}",
                request.getAmount(), request.getSourceCurrency(), request.getTargetCurrency());
        return exchangeRateHandler.getExchangeRate(request.getSourceCurrency(), request.getTargetCurrency())
                .map(exchangeRate -> {
                    Double convertedAmount = request.getAmount() * exchangeRate.getRate();
                    log.info("Exchange rate applied successfully: {}", exchangeRate);
                    return ExchangeRateResponse.builder()
                            .amount(request.getAmount())
                            .convertedAmount(convertedAmount)
                            .sourceCurrency(request.getSourceCurrency())
                            .targetCurrency(request.getTargetCurrency())
                            .rate(exchangeRate.getRate())
                            .build();
                })
                .doOnError(e -> log.error("Error applying exchange rate: {}", e.getMessage()))
                .switchIfEmpty(Mono.error(new NoDataFoundException("Exchange rate not found")));
    }

    @GetMapping
    @Operation(
            summary = "Get exchange rate",
            description = "Retrieves the exchange rate for the given currencies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Exchange rate retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExchangeRateModel.class),
                                    examples = @ExampleObject(
                                            value = "{\"sourceCurrency\": \"USD\", \"targetCurrency\": \"EUR\", \"rate\": 0.85}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Exchange rate not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"error\": \"Exchange rate not found\"}"
                                    )
                            )
                    )
            }
    )
    public Mono<ExchangeRateModel> getExchangeRate(@RequestParam String sourceCurrency,
                                                   @RequestParam String targetCurrency) {
        log.info("Retrieving exchange rate for sourceCurrency: {}, targetCurrency: {}", sourceCurrency, targetCurrency);
        return exchangeRateHandler.getExchangeRate(sourceCurrency, targetCurrency)
                .doOnSuccess(exchangeRate -> log.info("Exchange rate retrieved successfully: {}", exchangeRate))
                .doOnError(e -> log.error("Error retrieving exchange rate: {}", e.getMessage()))
                .switchIfEmpty(Mono.error(new NoDataFoundException("Exchange rate not found")));
    }

    @PostMapping("/update")
    @Operation(
            summary = "Update exchange rate",
            description = "Updates the exchange rate for the given currencies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Exchange rate updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExchangeRateModel.class),
                                    examples = @ExampleObject(
                                            value = "{\"sourceCurrency\": \"USD\", \"targetCurrency\": \"EUR\", \"rate\": 0.85}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid exchange rate",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"error\": \"Exchange rate must be greater than zero\"}"
                                    )
                            )
                    )
            }
    )
    public Mono<ExchangeRateModel> updateExchangeRate(@RequestBody UpdateExchangeRateRequest request) {
        log.info("Updating exchange rate for sourceCurrency: {}, targetCurrency: {}, rate: {}",
                request.getSourceCurrency(), request.getTargetCurrency(), request.getRate());
        if (request.getRate() <= 0) {
            log.error("Invalid exchange rate: {}", request.getRate());
            throw new InvalidExchangeRateException("Exchange rate must be greater than zero");
        }
        ExchangeRateModel exchangeRateModel = ExchangeRateModel.builder()
                .sourceCurrency(request.getSourceCurrency())
                .targetCurrency(request.getTargetCurrency())
                .rate(request.getRate())
                .build();
        return exchangeRateHandler.updateExchangeRate(exchangeRateModel)
                .doOnSuccess(updatedRate -> log.info("Exchange rate updated successfully: {}", updatedRate))
                .doOnError(e -> log.error("Error updating exchange rate: {}", e.getMessage()));
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all exchange rates with pagination",
            description = "Retrieves all exchange rates with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Exchange rates retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExchangeRateModel.class),
                                    examples = @ExampleObject(
                                            value = "{\"content\": [{\"id\": 1, \"sourceCurrency\": \"USD\", \"targetCurrency\": \"EUR\", \"rate\": 0.85}, {\"id\": 2, \"sourceCurrency\": \"EUR\", \"targetCurrency\": \"USD\", \"rate\": 1.18}], \"pageable\": {\"pageNumber\": 0, \"pageSize\": 4}, \"totalElements\": 2, \"totalPages\": 1}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No exchange rates found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"error\": \"No exchange rates found\"}"
                                    )
                            )
                    )
            }
    )
    public Mono<Page<ExchangeRateModel>> getAllExchangeRates(
            @Parameter(description = "Page number", example = "0") @RequestParam int page,
            @Parameter(description = "Page size", example = "4") @RequestParam int size,
            @Parameter(description = "Sort criteria", example = "rate,asc") @RequestParam(required = false) String sort,
            ServerWebExchange exchange) {
        log.info("Retrieving all exchange rates with pagination - page: {}, size: {}, sort: {}", page, size, sort);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort);
        return exchangeRateHandler.getAllExchangeRates(pageable)
                .doOnSuccess(pageResult -> log.info("Exchange rates retrieved successfully"))
                .doOnError(e -> log.error("Error retrieving exchange rates: {}", e.getMessage()))
                .flatMap(pageResult -> {
                    if (pageResult.isEmpty()) {
                        log.warn("No exchange rates found");
                        return Mono.error(new NoDataFoundException("No exchange rates found"));
                    }
                    return Mono.just(pageResult);
                });
    }
}