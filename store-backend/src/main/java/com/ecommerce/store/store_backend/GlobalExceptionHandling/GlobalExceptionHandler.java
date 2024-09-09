package com.ecommerce.store.store_backend.GlobalExceptionHandling;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle any generic exceptions
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<String> handleAllExceptions(Exception ex) {
//        logger.error("Exception: ", ex);
//        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<mGeneric.mApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new mGeneric.mApiResponse<>(500, "Internal server error",null));
    }
    // Handle resource not found exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.warn("Resource not found: ", ex);
        return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle invalid input exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Invalid input: ", ex);
        return new ResponseEntity<>("Invalid input: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
