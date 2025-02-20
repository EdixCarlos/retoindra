package com.edixs.retoindra.infrastructure.out.jpa.repository;

import com.edixs.retoindra.infrastructure.out.jpa.entity.ExchangeRateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    Optional<ExchangeRateEntity> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);
    Page<ExchangeRateEntity> findAll(Pageable pageable);
}