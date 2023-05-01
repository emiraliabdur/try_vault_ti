package com.example.tryvault_ti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjuster;

import static java.time.temporal.TemporalAdjusters.ofDateAdjuster;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@AllArgsConstructor
@Getter
public enum PeriodType {
    DAY(ofDateAdjuster(d -> d)),
    WEEK(previousOrSame(DayOfWeek.of(1)));

    private TemporalAdjuster temporalAdjuster;

}
