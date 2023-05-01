package com.example.tryvault_ti.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "fund_load_attempt")
public class FundLoadAttemptEntity {

    @Id
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;
}
