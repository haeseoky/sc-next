package com.ocean.scnext.api.application;

import com.ocean.scnext.api.domain.AResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AService {

    public Mono<AResponse> getAResponse() {

        return Mono.just(new AResponse("aname", 10))
            .delayElement(Duration.ofSeconds(3))
            .doOnNext(aResponse -> log.info("aResponse: {}", aResponse));
    }

    public AResponse getAResponseNormal() {
        return new AResponse("aname normal", 10);
    }

}
