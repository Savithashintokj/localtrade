package com.user.trader;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePhoneNumberException.class)
    public ResponseEntity<?> handleDuplicatePhoneNumberException(DuplicatePhoneNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)  // 409 Conflict is appropriate here
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    // Handle other exceptions...

    static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
        public int getStatus() { return status; }
        public String getMessage() { return message; }
    }
}
