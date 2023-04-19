package com.admin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

    private int code;
    private String message;
    private T data;

    public ResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
