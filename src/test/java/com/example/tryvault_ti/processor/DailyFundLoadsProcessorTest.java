package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import com.example.tryvault_ti.model.PeriodType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.example.tryvault_ti.model.PeriodType.DAY;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DailyFundLoadsProcessorTest {

    private static final Long CUSTOMER_ID = 100L;

    private AbstractPeriodFundLoadsProcessor dailyFundLoadsProcessor = new DailyFundLoadsProcessor(
            Map.of(DAY, new FundLoadPeriodCriteria(3, BigDecimal.valueOf(5000.00), DAY))
    );

    @ParameterizedTest
    @MethodSource("fundLoadsData")
    public void shouldProcessDailyPeriodsForSingleCustomer(List<FundLoad> fundLoads, List<FundLoadResult> expectedFundLoadResults) {
        Map<Long, FundLoadResult> actualResultMap = dailyFundLoadsProcessor.processFundLoads(CUSTOMER_ID, fundLoads);

        assertIterableEquals(expectedFundLoadResults, actualResultMap.values());
    }

    private static Stream<Arguments> fundLoadsData() {
        ZonedDateTime today = ZonedDateTime.now();

        return Stream.of(
                // 3 loads and 4000 < 5000 => all loads are accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(1000), today),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(2000), today),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(1000), today)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, true)
                        )
                ),
                // 4 loads => 4th is not accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(1000), today),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(2000), today),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(1000), today),
                                new FundLoad(4L, CUSTOMER_ID, BigDecimal.valueOf(1000), today)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, true),
                                new FundLoadResult(4L, CUSTOMER_ID, false)
                        )
                ),
                // 2 loads, 2nd load exceeds limit => 2nd is not accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(1000), today),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(4500), today)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, false)
                        )
                ),
                // 4 loads in 2 different days, all are accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(4000), today),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(500), today),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(4000), today.minusDays(1)),
                                new FundLoad(4L, CUSTOMER_ID, BigDecimal.valueOf(500), today.minusDays(1))
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, true),
                                new FundLoadResult(4L, CUSTOMER_ID, true)
                        )
                )
        );
    }
}