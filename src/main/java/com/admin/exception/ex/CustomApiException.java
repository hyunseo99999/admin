package com.admin.exception.ex;

import java.util.function.Supplier;

public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}
