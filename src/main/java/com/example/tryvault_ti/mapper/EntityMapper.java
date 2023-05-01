package com.example.tryvault_ti.mapper;

import com.example.tryvault_ti.entity.FundLoadAttemptEntity;
import com.example.tryvault_ti.entity.FundLoadResultEntity;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface EntityMapper {

    FundLoadResultEntity toResultEntity(FundLoadResult result);

    List<FundLoadResultEntity> toResultEntities(List<FundLoadResult> results);

    @Mapping(source = "loadAmount", target = "amount")
    @Mapping(source = "time", target = "dateTime")
    FundLoadAttemptEntity toLoadAttemptEntity(FundLoad fundLoad);

    List<FundLoadAttemptEntity> toLoadAttemptEntities(List<FundLoad> fundLoads);
}
