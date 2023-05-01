package com.example.tryvault_ti.processor;

import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;

import java.util.List;

public interface FundLoadProcessor {
    List<FundLoadResult> processFundLoads(List<FundLoad> fundLoads);
}
