package com.ocean.scnext.api.config.filter;

import java.nio.charset.StandardCharsets;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebfluxRequestLogFilter  implements WebFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(WebfluxRequestLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("webflux Request: {} {}", request.getMethod(), request.getURI());

        // 요청 파라미터 로깅
        request.getQueryParams().forEach((key, value) ->
            log.info("webflux Request Param: {} = {}", key, String.join(",", value)));

        // 응답 로깅을 위한 데코레이터
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                return super.writeWith(Flux.from(body)
                    .doOnNext(buffer -> {
                        byte[] content = new byte[buffer.readableByteCount()];
                        buffer.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        log.info("webflux Response: Status={}, Body={}",
                            getStatusCode(), responseBody);
                    }));
            }
        };

        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}