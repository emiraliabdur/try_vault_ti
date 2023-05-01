package com.example.tryvault_ti.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "fund_load_result")
public class FundLoadResultEntity {

    @Id
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "accepted")
    private Boolean accepted;
}
