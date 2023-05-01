package com.example.tryvault_ti.model;

import com.example.tryvault_ti.mapping.LoadAmountDeserializer;
import com.example.tryvault_ti.mapping.LoadAmountSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FundLoad {

    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonSerialize(using = LoadAmountSerializer.class)
    @JsonDeserialize(using = LoadAmountDeserializer.class)
    @JsonProperty("load_amount")
    private BigDecimal loadAmount;

    @JsonProperty("time")
    private ZonedDateTime time;
}
