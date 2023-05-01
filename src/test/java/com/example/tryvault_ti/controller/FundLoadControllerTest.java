package com.example.tryvault_ti.controller;

import com.example.tryvault_ti.service.FundLoadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
class FundLoadControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FundLoadService fundLoadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postFundLoadsFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "input.txt",
                MULTIPART_FORM_DATA_VALUE,
                fundLoadsData().getBytes()
        );

        MvcResult mvcResult = mockMvc.perform(multipart("/upload")
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseData = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponseData(), responseData);
    }

    private String fundLoadsData() {
        return "{\"id\":\"10285\",\"customer_id\":\"171\",\"load_amount\":\"$4961.88\",\"time\":\"2000-01-17T21:01:12Z\"}\n" +
                "{\"id\":\"7558\",\"customer_id\":\"800\",\"load_amount\":\"$3680.19\",\"time\":\"2000-01-17T22:02:34Z\"}\n";
    }

    private String expectedResponseData() {
        return "{\"id\":7558,\"customerId\":800,\"accepted\":true}\n" +
                "{\"id\":10285,\"customerId\":171,\"accepted\":true}\n";
    }

}