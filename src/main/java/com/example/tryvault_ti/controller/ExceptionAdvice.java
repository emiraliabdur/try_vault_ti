package com.example.tryvault_ti.controller;

import com.example.tryvault_ti.exception.ErrorResult;
import com.example.tryvault_ti.exception.InputDataLineParseException;
import com.example.tryvault_ti.exception.OutputResultFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResult> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResult(exc.getMessage()));
    }

    @ExceptionHandler(OutputResultFileException.class)
    public ResponseEntity<ErrorResult> handleMaxSizeException(OutputResultFileException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResult(exc.getMessage()));
    }

    @ExceptionHandler(InputDataLineParseException.class)
    public ResponseEntity<ErrorResult> handleMaxSizeException(InputDataLineParseException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(exc.getMessage()));
    }
}
