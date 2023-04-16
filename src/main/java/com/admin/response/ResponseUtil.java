package com.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseUtil<T> {

    private int code;
    private String message;
    private T data;

    public ResponseUtil(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseUtil(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
