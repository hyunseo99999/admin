package com.admin.exception;


import com.admin.exception.ex.CustomApiException;
import com.admin.exception.ex.CustomValidationException;
import com.admin.response.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseUtil<>(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> apiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseUtil(400, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    // 404예외처리 핸들러
   	@ExceptionHandler(NoHandlerFoundException.class)
   	public ResponseEntity<?> handle404(NoHandlerFoundException e){
        return new ResponseEntity<>(new ResponseUtil(404, "404 페이지", null), HttpStatus.NOT_FOUND);
   	}

}
