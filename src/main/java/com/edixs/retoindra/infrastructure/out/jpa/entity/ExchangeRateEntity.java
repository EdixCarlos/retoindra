package com.edixs.retoindra.infrastructure.out.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity(name = "exchange_rate")
@Getter
@Setter
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceCurrency;

    @Column(nullable = false)
    private String targetCurrency;

    @Column(nullable = false)
    private Double rate;
}