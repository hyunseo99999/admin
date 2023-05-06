package com.admin.response;

import com.admin.web.user.dto.UserRespDto;
import com.admin.web.user.dto.UserRespDto.LoginRespDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter @Setter
public class ResponseUtil<T> {

    public static void fail(HttpServletResponse response, String message, HttpStatus httpStatus) {
        try {
            response.setContentType("application/json;");
            response.setCharacterEncoding("utf-8");
            response.setStatus(httpStatus.value());

            ResponseDto<?> responseDto = new ResponseDto<>(httpStatus.value(), message, null);
            ObjectMapper mapper = new ObjectMapper();
            String responseBody = mapper.writeValueAsString(responseDto);
            response.getWriter().println(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void success(HttpServletResponse response, Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            ResponseDto<?> responseDto = new ResponseDto<>(200, "로그인 성공", data);
            String responseBody = mapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
