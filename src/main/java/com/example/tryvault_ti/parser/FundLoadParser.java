package com.example.tryvault_ti.parser;

import com.example.tryvault_ti.model.FundLoad;

import java.io.InputStream;
import java.util.List;

public interface FundLoadParser {
    List<FundLoad> parseInputData(InputStream data);
}
