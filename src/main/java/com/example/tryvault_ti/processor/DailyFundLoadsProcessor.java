package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import com.example.tryvault_ti.model.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DailyFundLoadsProcessor extends AbstractPeriodFundLoadsProcessor {

    @Autowired
    public DailyFundLoadsProcessor(Map<PeriodType, FundLoadPeriodCriteria> fundPeriodLimitMap) {
        this.periodType = PeriodType.DAY;
        this.periodLimit = fundPeriodLimitMap.get(periodType);
    }
}
