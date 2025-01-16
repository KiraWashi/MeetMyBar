package org.meetmybar.meetmybarapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred while processing your request",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class ErrorResponse {
        private final int status;
        private final String message;
        private final String details;

        public ErrorResponse(int status, String message, String details) {
            this.status = status;
            this.message = message;
            this.details = details;
        }

        // Getters
        public int getStatus() { return status; }
        public String getMessage() { return message; }
        public String getDetails() { return details; }
    }
}