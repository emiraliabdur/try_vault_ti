package com.example.tryvault_ti.criteria;

import com.example.tryvault_ti.model.AggregatedFundLoad;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FundLoadPeriodCriteria {
    private Integer numberOfLoads;
    private BigDecimal totalLoadAmount;
    private PeriodType periodType;

    public boolean meetCriteria(FundLoad currentFundLoad, AggregatedFundLoad aggregatedFundLoad) {
        BigDecimal currentTotalAmount = currentFundLoad.getLoadAmount().add(aggregatedFundLoad.getLoadAmount());
        int currentLoads = aggregatedFundLoad.getNumberOfLoads() + 1;

        return this.periodType == aggregatedFundLoad.getPeriodType() &&
                this.getTotalLoadAmount().compareTo(currentTotalAmount) >= 0 &&
                this.getNumberOfLoads() >= currentLoads;
    }

}
