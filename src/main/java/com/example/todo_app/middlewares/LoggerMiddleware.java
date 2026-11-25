package com.example.todo_app.middlewares;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggerMiddleware implements Filter {

    private static final Logger log =LoggerFactory.getLogger(LoggerMiddleware.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      long start=System.nanoTime();
      var req=(HttpServletRequest) request;
       try {
           chain.doFilter(request,response);
       }finally {
           long end=System.nanoTime();
           log.info("{} {} ->{} ({} ms)",req.getMethod(),req.getRequestURI(),((HttpServletResponse)response).getStatus(),(end-start)/1_000_000);
       }
    }
}
