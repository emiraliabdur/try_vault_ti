package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.criteria.FundLoadPeriodCriteria;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.example.tryvault_ti.model.PeriodType.WEEK;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class WeeklyFundLoadsProcessorTest {

    private static final Long CUSTOMER_ID = 100L;

    private AbstractPeriodFundLoadsProcessor weeklyFundLoadsProcessor = new WeeklyFundLoadsProcessor(
            Map.of(WEEK, new FundLoadPeriodCriteria(Integer.MAX_VALUE, BigDecimal.valueOf(20000.00), WEEK))
    );

    @ParameterizedTest
    @MethodSource("fundLoadsData")
    public void shouldProcessDailyPeriodsForSingleCustomer(List<FundLoad> fundLoads, List<FundLoadResult> expectedFundLoadResults) {
        Map<Long, FundLoadResult> actualResultMap = weeklyFundLoadsProcessor.processFundLoads(CUSTOMER_ID, fundLoads);

        assertIterableEquals(expectedFundLoadResults, actualResultMap.values());
    }

    private static Stream<Arguments> fundLoadsData() {
        ZonedDateTime monday = ZonedDateTime.now().with(previousOrSame(DayOfWeek.of(1)));
        ZonedDateTime tuesday = ZonedDateTime.now().with(previousOrSame(DayOfWeek.of(2)));
        ZonedDateTime friday = ZonedDateTime.now().with(previousOrSame(DayOfWeek.of(5)));
        ZonedDateTime nextMonday = ZonedDateTime.now().with(nextOrSame(DayOfWeek.of(1)));

        return Stream.of(
                // 3 loads within same week => all loads are accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(10000), monday),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(2000), tuesday),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(7000), friday)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, true)
                        )
                ),
                // 3 loads within same week, friday's load exceeds limit => 3rd is not accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(10000), monday),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(2000), tuesday),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(9000), friday)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, false)
                        )
                ),
                // 2 loads within same week and 1 load from other week => all loads are accepted
                Arguments.of(
                        List.of(
                                new FundLoad(1L, CUSTOMER_ID, BigDecimal.valueOf(10000), monday),
                                new FundLoad(2L, CUSTOMER_ID, BigDecimal.valueOf(2000), tuesday),
                                new FundLoad(3L, CUSTOMER_ID, BigDecimal.valueOf(19000), nextMonday)
                        ),
                        List.of(
                                new FundLoadResult(1L, CUSTOMER_ID, true),
                                new FundLoadResult(2L, CUSTOMER_ID, true),
                                new FundLoadResult(3L, CUSTOMER_ID, true)
                        )
                )
        );
    }
}