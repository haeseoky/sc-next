package com.ocean.scnext.api.application;

import com.ocean.scnext.api.domain.BResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BService {

    public Mono<BResponse> getBResponse() {
        return Mono.just(new BResponse("name"))
            .delayElement(Duration.ofSeconds(2))
            .doOnNext(bResponse -> log.info("bResponse: {}", bResponse));
    }

    public BResponse getBResponseNormal() {
        return new BResponse("name");

    }
}
