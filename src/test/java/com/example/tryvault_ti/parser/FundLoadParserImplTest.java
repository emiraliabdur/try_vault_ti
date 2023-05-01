package com.example.tryvault_ti.parser;

import com.example.tryvault_ti.exception.InputDataLineParseException;
import com.example.tryvault_ti.model.FundLoad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FundLoadParserImplTest {

    @Autowired
    private FundLoadParser fundLoadParser;

    @Test
    public void shouldParseValidInputFile() {
        List<FundLoad> fundLoads = fundLoadParser.parseInputData(getResourceAsStream("/valid_input_data.txt"));

        assertEquals(3, fundLoads.size());
        fundLoads.forEach(fundLoad -> {
            assertNotNull(fundLoad.getId());
            assertNotNull(fundLoad.getCustomerId());
            assertNotNull(fundLoad.getLoadAmount());
            assertNotNull(fundLoad.getTime());
        });
    }

    @Test
    public void shouldNotParseInvalidInputFile() {
        InputDataLineParseException thrown = Assertions.assertThrows(InputDataLineParseException.class, () ->
                fundLoadParser.parseInputData(getResourceAsStream("/invalid_input_data.txt"))
        );

        Assertions.assertEquals("File contains invalid data on the line 4: some_invalid_line", thrown.getMessage());
    }

    private InputStream getResourceAsStream(String name) {
        return this.getClass().getResourceAsStream(name);
    }
}