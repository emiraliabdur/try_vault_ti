package com.example.tryvault_ti.controller;

import com.example.tryvault_ti.service.FundLoadService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FundLoadController {

    private final FundLoadService fundLoadService;

    public FundLoadController(FundLoadService fundLoadService) {
        this.fundLoadService = fundLoadService;
    }

    @PostMapping(value = "upload")
    public ResponseEntity<Resource> uploadFundLoadsFile(
            @RequestHeader HttpHeaders headers,
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        File resultFile = fundLoadService.processFundLoadData(file.getInputStream());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.txt");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(resultFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(resultFile));
    }
}
