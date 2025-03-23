package com.ocean.scnext.api.reator;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
public class Example8_1 {

    @Test
    void test() {
        Flux.range(1,5)
            .doOnRequest(data -> log.info("# doOnRequest: {}", data))
            .subscribe(
                new BaseSubscriber<>(){
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @SneakyThrows
                    @Override
                    protected void hookOnNext(Integer value) {
                        Thread.sleep(2000L);
                        log.info("# hookOnNExt: {}", value);
                        request(2);
                    }
                });

    }
}
