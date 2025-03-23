package com.ocean.scnext.api.application;

import com.ocean.scnext.api.domain.AResponse;
import com.ocean.scnext.api.domain.BResponse;
import com.ocean.scnext.api.domain.CResponse;
import com.ocean.scnext.api.domain.CombinedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParallelService {

    private final AService aService;
    private final BService bService;
    private final CService cService;


    public Mono<CombinedResult> waitAll() {

        return Mono.zip(
                    aService.getAResponse().switchIfEmpty(Mono.error(new RuntimeException("A service returned empty"))).doOnError(e -> log.error("aResponse error: , e")),
                    bService.getBResponse().switchIfEmpty(Mono.error(new RuntimeException("B service returned empty"))).doOnError(e -> log.error("bResponse error: , e")),
                    cService.getCResponse().switchIfEmpty(Mono.error(new RuntimeException("C service returned empty"))).doOnError(e -> log.error("cResponse error: , e")))
                .doOnNext(response -> log.info("response: {}", response))
//                .publishOn(Schedulers.boundedElastic())
                .map(tuple -> {
                        AResponse t1 = tuple.getT1();
                        BResponse t2 = tuple.getT2();
                        CResponse t3 = tuple.getT3();
                        return new CombinedResult(t1, t2, t3);
                    }
                ).doOnError(e -> log.error("error: , e"));
    }

//    public Mono<CombinedResult> waitAllFromNormal(){
//        Mono<AResponse> aResponseMono = Mono.fromCallable(aService.getAResponseNormal());
//        return Mono.zip()
//    }


//    public CombinedResult wait(){
//        Mono<AResponse> aResponseMono = aService.getAResponse();
//        Mono<BResponse> bResponseMono = bService.getBResponse();
//        Mono<CResponse> cResponseMono = cService.getCResponse();
//
//        Flux<Record> merge = Flux.merge(aResponseMono.flux(), bResponseMono.flux(), cResponseMono.flux());
//
//
//
//    }
}
