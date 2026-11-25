package com.example.todo_app.middlewares;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        ServletServerHttpResponse servletResponse =
                (ServletServerHttpResponse) response;
        int status = servletResponse.getServletResponse().getStatus();
        HttpStatus s = HttpStatus.valueOf(status);

        if (body instanceof ApiResponse<?>) {
            return body;
        }
        boolean isSuccess = s.is2xxSuccessful() || s.is1xxInformational();

        return new ApiResponse<>(isSuccess,
                Instant.now(),
                status,
                body,
                request.getURI().getPath()
        );
    }

    public record ApiResponse<T>(
            boolean success,
            Instant timestamp,
            int status,
            T data,
            String path
    ) {
    }
}
