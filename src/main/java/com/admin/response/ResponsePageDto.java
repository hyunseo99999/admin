package com.admin.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ResponsePageDto<T> {
    private int code;
    private String message;

    private int page;
    private int size;

    private int totalPage;

    private Long totalCount;
    private List<PageImpl> data;

    public ResponsePageDto(int code, String message, Page<T> data) {
        this.code = code;
        this.message = message;
        this.data = (List<PageImpl>) data.getContent();
        this.page = data.getPageable().getPageNumber() + 1;
        this.size = data.getSize();
        this.totalCount = data.getTotalElements();
        this.totalPage = data.getTotalPages();
    }
}
