package com.ocean.scnext.api.application;

import com.ocean.scnext.api.domain.CResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CService {

    public Mono<CResponse> getCResponse() {
        return Mono.just(new CResponse("000", "good", "data"))
            .delayElement(Duration.ofSeconds(1))
            .doOnNext(cResponse -> log.info("cResponse: {}", cResponse));
    }

    public CResponse getCResponseNormal() {
        return new CResponse("000", "good", "data");
    }

}
