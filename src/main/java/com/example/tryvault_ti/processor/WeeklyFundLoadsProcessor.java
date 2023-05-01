package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import com.example.tryvault_ti.model.PeriodType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeeklyFundLoadsProcessor extends AbstractPeriodFundLoadsProcessor {

    public WeeklyFundLoadsProcessor(Map<PeriodType, FundLoadPeriodCriteria> fundPeriodLimitMap) {
        this.periodType = PeriodType.WEEK;
        this.periodLimit = fundPeriodLimitMap.get(periodType);
    }
}
