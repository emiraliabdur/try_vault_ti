package com.example.tryvault_ti.exception;

public class InputDataLineParseException extends RuntimeException {
    private static final String ERROR_MSG = "File contains invalid data on the line %s: %s";
    private String dataLine;
    private Integer lineNumber;

    public InputDataLineParseException(Integer lineNumber, String dataLine) {
        super(String.format(ERROR_MSG, lineNumber, dataLine));
    }
}
