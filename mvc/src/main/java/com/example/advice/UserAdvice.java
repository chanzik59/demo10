package com.example.advice;

import com.example.entity.Student;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author czq
 * @date 2023/10/19 15:51
 * @Description:
 */
@ControllerAdvice
public class UserAdvice implements ResponseBodyAdvice<Object> {



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object  common(Exception e){
        e.printStackTrace();
        Student student = new Student();
        student.setId("10");
        student.setName("里斯");
        return student;

    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Student student = new Student();
        student.setId("10");
        student.setName("20");

        return student;
    }
}
