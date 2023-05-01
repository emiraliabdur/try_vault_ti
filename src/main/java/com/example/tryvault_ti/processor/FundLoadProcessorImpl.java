package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FundLoadProcessorImpl implements FundLoadProcessor {

    private final List<AbstractPeriodFundLoadsProcessor> periodFundLoadsProcessors;

    @Autowired
    public FundLoadProcessorImpl(List<AbstractPeriodFundLoadsProcessor> periodFundLoadsProcessors) {
        this.periodFundLoadsProcessors = periodFundLoadsProcessors;
    }

    @Override
    public List<FundLoadResult> processFundLoads(List<FundLoad> fundLoads) {
        Map<Long, List<FundLoad>> customerFundLoadsMap = removeFromDuplicates(fundLoads)
                .stream()
                .collect(Collectors.groupingBy(FundLoad::getCustomerId));

        return customerFundLoadsMap.entrySet()
                .stream()
                .map(this::processCustomerFundLoadEntries)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Collection<FundLoadResult> processCustomerFundLoadEntries(Map.Entry<Long, List<FundLoad>> e) {
        List<FundLoad> customerFundLoads = e.getValue()
                .stream()
                .sorted(Comparator.comparing(FundLoad::getTime))
                .collect(Collectors.toList());
        Long customerId = e.getKey();

        return periodFundLoadsProcessors
                .stream()
                .map(periodProcessor -> periodProcessor.processFundLoads(customerId, customerFundLoads))
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        FundLoadResult::merge
                ))
                .values();
    }

    private Collection<FundLoad> removeFromDuplicates(List<FundLoad> fundLoads) {
        return fundLoads.stream()
                .collect(Collectors.toMap(FundLoad::getId, Function.identity(), (a1, a2) -> a1))
                .values();
    }
}
