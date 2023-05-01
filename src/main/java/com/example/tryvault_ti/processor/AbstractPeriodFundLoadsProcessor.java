package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import com.example.tryvault_ti.model.AggregatedFundLoad;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import com.example.tryvault_ti.model.PeriodType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
public abstract class AbstractPeriodFundLoadsProcessor {

    protected PeriodType periodType;
    protected FundLoadPeriodCriteria periodLimit;

    public Map<Long, FundLoadResult> processFundLoads(Long customerId, List<FundLoad> fundLoads) {
        Map<ZonedDateTime, List<FundLoad>> fundLoadsByPeriodMap = fundLoads
                .stream()
                .collect(Collectors.groupingBy(
                        fl -> fl.getTime().with(periodType.getTemporalAdjuster()).truncatedTo(DAYS),
                        LinkedHashMap::new, Collectors.toList()
                ));

        return fundLoadsByPeriodMap
                .values()
                .stream()
                .flatMap(fundLoadsForPeriod -> {
                    log.info("{} fund loads processing is about to start for customer {}", periodType, customerId);
                    AggregatedFundLoad aggregatedFundLoadForDay = new AggregatedFundLoad(customerId, periodType, 0, BigDecimal.ZERO);

                    return fundLoadsForPeriod
                            .stream()
                            .sorted(Comparator.comparing(FundLoad::getTime))
                            .map(fundLoad -> {
                                if (periodLimit.meetCriteria(fundLoad, aggregatedFundLoadForDay)) {
                                    log.info("Fund load with id {} is accepted", fundLoad.getId());

                                    aggregatedFundLoadForDay.addFund(fundLoad);
                                    return new FundLoadResult(fundLoad.getId(), customerId, true);
                                }

                                log.info("Fund load with id {} is not accepted", fundLoad.getId());
                                return new FundLoadResult(fundLoad.getId(), customerId, false);
                            });
                })
                .collect(Collectors.toMap(FundLoadResult::getId, Function.identity()));
    }

}
