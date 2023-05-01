package com.example.tryvault_ti.service;

import com.example.tryvault_ti.entity.FundLoadAttemptEntity;
import com.example.tryvault_ti.entity.FundLoadResultEntity;
import com.example.tryvault_ti.exception.OutputResultFileException;
import com.example.tryvault_ti.mapper.EntityMapper;
import com.example.tryvault_ti.model.FundLoad;
import com.example.tryvault_ti.model.FundLoadResult;
import com.example.tryvault_ti.parser.FundLoadParser;
import com.example.tryvault_ti.processor.FundLoadProcessor;
import com.example.tryvault_ti.repository.FundLoadAttemptRepository;
import com.example.tryvault_ti.repository.FundLoadResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Service
@Transactional
public class FundLoadServiceImpl implements FundLoadService {

    private final FundLoadParser fundLoadParser;
    private final FundLoadProcessor fundLoadProcessor;
    private final FundLoadAttemptRepository fundLoadAttemptRepository;
    private final FundLoadResultRepository fundLoadResultRepository;
    private final EntityMapper entityMapper;
    private final ObjectMapper objectMapper;

    public FundLoadServiceImpl(FundLoadParser fundLoadParser, FundLoadProcessor fundLoadProcessor,
                               FundLoadAttemptRepository fundLoadAttemptRepository, FundLoadResultRepository fundLoadResultRepository,
                               EntityMapper entityMapper, ObjectMapper objectMapper) {
        this.fundLoadParser = fundLoadParser;
        this.fundLoadProcessor = fundLoadProcessor;
        this.fundLoadAttemptRepository = fundLoadAttemptRepository;
        this.fundLoadResultRepository = fundLoadResultRepository;
        this.entityMapper = entityMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public File processFundLoadData(InputStream fundLoadData) {
        List<FundLoad> fundLoads = fundLoadParser.parseInputData(fundLoadData);

        List<FundLoadAttemptEntity> attemptEntities = entityMapper.toLoadAttemptEntities(fundLoads);
        fundLoadAttemptRepository.saveAll(attemptEntities);

        List<FundLoadResult> fundLoadResults = fundLoadProcessor.processFundLoads(fundLoads);
        List<FundLoadResultEntity> resultEntities = entityMapper.toResultEntities(fundLoadResults);
        fundLoadResultRepository.saveAll(resultEntities);

        return storeToFile(fundLoadResults);
    }

    private File storeToFile(List<FundLoadResult> fundLoadResults) {
        File results = null;
        try {
            results = File.createTempFile("fundLoadResults", ".txt");
        } catch (IOException ex) {
            throw new OutputResultFileException("Failed to create result file",  ex.getCause());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(results))) {
            for (FundLoadResult result : fundLoadResults) {
                writer.write(objectMapper.writeValueAsString(result));
                writer.newLine();
            }

        } catch (IOException ex) {
            throw new OutputResultFileException("Failed to save results",  ex.getCause());
        }
        return results;
    }
}
