package com.example.tryvault_ti.config;

import com.example.tryvault_ti.model.PeriodType;
import com.example.tryvault_ti.repository.PeriodLoadLimitRepository;
import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class PeriodLimitConfiguration {

    @Bean
    public Map<PeriodType, FundLoadPeriodCriteria> fundPeriodLimitMap(PeriodLoadLimitRepository periodLoadLimitRepository) {
        return periodLoadLimitRepository.findAll()
                .stream()
                .map(limitsEntity -> new FundLoadPeriodCriteria(limitsEntity.getNumberOfLoads(), limitsEntity.getTotalLoadAmount(), limitsEntity.getPeriodType()))
                .collect(Collectors.toMap(FundLoadPeriodCriteria::getPeriodType, Function.identity()));
    }
}
