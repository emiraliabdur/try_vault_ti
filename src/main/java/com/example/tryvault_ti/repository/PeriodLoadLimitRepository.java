package com.example.tryvault_ti.repository;

import com.example.tryvault_ti.entity.PeriodLoadLimitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodLoadLimitRepository extends JpaRepository<PeriodLoadLimitsEntity, String> {

}
