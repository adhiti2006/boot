package com.ss.demo.resource;

import com.ss.demo.domain.ErrorInfo;
import com.ss.demo.exception.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerResourceAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorInfo> notAuthorizedException(NotAuthorizedException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode("ACCESS_DENIED");
        errorInfo.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }
}
