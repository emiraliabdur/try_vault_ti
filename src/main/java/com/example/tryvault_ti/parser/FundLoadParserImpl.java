package com.example.tryvault_ti.parser;

import com.example.tryvault_ti.exception.InputDataLineParseException;
import com.example.tryvault_ti.model.FundLoad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class FundLoadParserImpl implements FundLoadParser {

    private final ObjectMapper objectMapper;

    @Autowired
    public FundLoadParserImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<FundLoad> parseInputData(InputStream data) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(data, UTF_8));

        AtomicInteger lineNumber = new AtomicInteger(0);
        return reader.lines()
                .map(line -> {
                    lineNumber.getAndIncrement();
                    try {
                        return objectMapper.readValue(line, FundLoad.class);
                    } catch (JsonProcessingException e) {
                        throw new InputDataLineParseException(lineNumber.get(), line);
                    }
                })
                .collect(Collectors.toList());
    }
}
