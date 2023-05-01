package com.example.tryvault_ti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class AggregatedFundLoad {
    private Long customerId;
    private PeriodType periodType;
    private Integer numberOfLoads;
    private BigDecimal loadAmount;

    public void addFund(FundLoad fundLoad) {
        this.numberOfLoads++;
        this.loadAmount = loadAmount.add(fundLoad.getLoadAmount());
    }
}
