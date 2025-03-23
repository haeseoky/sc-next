package com.ocean.scnext.api.presentation;

import com.ocean.scnext.api.application.ParallelService;
import com.ocean.scnext.api.domain.CombinedResult;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ParallelService parallelService;


    @GetMapping(value = "/{orderId}")
    public String getOrder(@PathVariable String orderId) throws InterruptedException {
        log.info("Get order id start");
        Thread.sleep(1000);

        return "order(" + orderId + ")";
    }

    @GetMapping(value = "/{orderId}/details")
    public List<String> getOrderDetails(@PathVariable String orderId) {
        return List.of(orderId);
    }

    @GetMapping(value = "/parallel")
    public Mono<CombinedResult> getParallelOrders() {
        log.info("getParallelOrders start");
        return parallelService.waitAll().doOnSubscribe(s -> log.info("Subscription started"))
            .doOnSuccess(result -> log.info("Result received: {}", result));
    }

    @GetMapping(value = "/parallelBlock")
    public CombinedResult waitAllBlock() {
        log.info("getParallelOrders start");
        return parallelService.waitAllBlock();
    }

}
