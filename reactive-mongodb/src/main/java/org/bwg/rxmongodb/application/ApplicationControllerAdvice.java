package org.bwg.rxmongodb.application;

import lombok.extern.slf4j.Slf4j;
import org.bwg.rxmongodb.model.exception.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    ResponseEntity<?> recordNotFound(RecordNotFoundException ex) {
        log.debug("handling exception::" + ex);
        return ResponseEntity.notFound().build();
    }
}
