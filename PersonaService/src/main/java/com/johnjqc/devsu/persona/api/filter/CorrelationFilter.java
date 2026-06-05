package com.johnjqc.devsu.persona.api.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID = "X-Correlation-Id";
    public static final String TRANSACTION_ID = "X-Transaction-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var correlationId = request.getHeader(CORRELATION_ID);

        var transactionId = request.getHeader(TRANSACTION_ID);

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        if (transactionId == null) {
            transactionId = UUID.randomUUID().toString();
        }

        response.addHeader(
                CORRELATION_ID,
                correlationId
        );

        response.addHeader(
                TRANSACTION_ID,
                transactionId
        );

        filterChain.doFilter(
                request,
                response
        );
    }
}
