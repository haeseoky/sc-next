package com.ocean.scnext.api.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class RequestLogFilter {
//    extends
//} OncePerRequestFilter implements Ordered {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 요청 로깅
//        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
//        request.getParameterMap().forEach((key, value) ->
//            log.info("Request Param: {} = {}", key, String.join(",", value)));
//
//        // 응답 로깅을 위해 래핑
////        ResponseWrapper responseWrapper = new ResponseWrapper(response);
//
//        filterChain.doFilter(request, response);
//
//        // 응답 로깅
////        String responseBody = new String(responseWrapper.getCopy());
////        log.info("Response: Status={}, Body={}", response.getStatus(), responseBody);
////
////        response.getOutputStream().write(responseWrapper.getCopy());
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE + 1;
//    }
}
