package com.example.tryvault_ti.entity;

import com.example.tryvault_ti.model.PeriodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "period_load_limits")
public class PeriodLoadLimitsEntity {

    @Id
    @Column(name = "period_type")
    @Enumerated(EnumType.STRING)
    private PeriodType periodType;

    @Column(name = "number_of_loads")
    private Integer numberOfLoads;

    @Column(name = "total_load_amount", precision = 10, scale = 2)
    private BigDecimal totalLoadAmount;
}
