package com.ocean.scnext.api.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootTest
class OrderControllerTest {

    private static final AtomicInteger sequence = new AtomicInteger(0);
    private static final AtomicInteger successCount = new AtomicInteger(0);
    private static final AtomicInteger failureCount = new AtomicInteger(0);
    private static final int REQUEST_COUNT = 10;
    // 타겟 URL 설정
    String url = "http://localhost:9900/orders/parallel";
    String url2 = "http://localhost:9900/orders/parallelBlock";
    private static final List<Long> responseTimes = new ArrayList<>();

    @Test
    void getParallelBlockOrdersByPhaser() throws InterruptedException {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(REQUEST_COUNT);

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // Phaser 생성 - 1로 초기화 (메인 스레드가 참여)
        Phaser phaser = new Phaser(1);

        // 전체 실행 시간 측정
        StopWatch totalStopWatch = new StopWatch("Total Execution");
        totalStopWatch.start();

        log.info("Starting test with {} concurrent requests", REQUEST_COUNT);

        // 모든 작업 스레드 등록 및 준비
        for (int i = 0; i < REQUEST_COUNT; i++) {
            final int requestId = i + 1;
            phaser.register(); // 새 파티(작업) 등록

            executorService.execute(() -> {
                try {
                    log.info("Thread {} is ready and waiting", requestId);

                    // 1단계: 모든 스레드가 준비될 때까지 대기
                    int phase = phaser.arriveAndAwaitAdvance();
                    log.debug("Thread {} started in phase {}", requestId, phase);

                    // 개별 요청 시간 측정
                    StopWatch stopWatch = new StopWatch("Request-" + requestId);
                    stopWatch.start();

                    try {
                        // HTTP 요청 실행
                        ResponseEntity<String> response = restTemplate.getForEntity(url2, String.class);

                        stopWatch.stop();
                        long responseTimeMs = stopWatch.getTotalTimeMillis();
                        responseTimes.add(responseTimeMs);

                        if (response.getStatusCode().is2xxSuccessful()) {
                            successCount.incrementAndGet();
                            log.info("Request {}: completed in {} ms, Status: {}",
                                requestId, responseTimeMs, response.getStatusCode());
                        } else {
                            failureCount.incrementAndGet();
                            log.warn("Request {}: completed in {} ms, but failed with status: {}",
                                requestId, responseTimeMs, response.getStatusCode());
                        }
                    } catch (Exception e) {
                        stopWatch.stop();
                        failureCount.incrementAndGet();
                        log.error("Request {} failed after {} ms: {}",
                            requestId, stopWatch.getTotalTimeMillis(), e.getMessage());
                    }
                } finally {
                    // 2단계: 작업 완료 신호
                    phaser.arrive();
                }
            });
        }

        // 1단계: 모든 스레드가 준비되면 동시에 시작
        log.info("Main thread waiting for all threads to be ready");
        phaser.arriveAndAwaitAdvance();
        log.info("All threads started executing requests simultaneously");

        // 2단계: 모든 작업이 완료될 때까지 대기
        log.info("Main thread waiting for all requests to complete");
        phaser.arriveAndAwaitAdvance();
        log.info("All requests completed");

        totalStopWatch.stop();

        // 결과 통계 계산 및 출력
        printStatistics(totalStopWatch.getTotalTimeMillis());

        // 스레드 풀 종료
        executorService.shutdown();
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            log.warn("Executor did not terminate in the specified time.");
            executorService.shutdownNow();
        }
    }


    @Test
    void getParallelOrdersByPhaser() throws InterruptedException {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(REQUEST_COUNT);

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // Phaser 생성 - 1로 초기화 (메인 스레드가 참여)
        Phaser phaser = new Phaser(1);

        // 전체 실행 시간 측정
        StopWatch totalStopWatch = new StopWatch("Total Execution");
        totalStopWatch.start();

        log.info("Starting test with {} concurrent requests", REQUEST_COUNT);

        // 모든 작업 스레드 등록 및 준비
        for (int i = 0; i < REQUEST_COUNT; i++) {
            final int requestId = i + 1;
            phaser.register(); // 새 파티(작업) 등록

            executorService.execute(() -> {
                try {
                    log.info("Thread {} is ready and waiting", requestId);

                    // 1단계: 모든 스레드가 준비될 때까지 대기
                    int phase = phaser.arriveAndAwaitAdvance();
                    log.debug("Thread {} started in phase {}", requestId, phase);

                    // 개별 요청 시간 측정
                    StopWatch stopWatch = new StopWatch("Request-" + requestId);
                    stopWatch.start();

                    try {
                        // HTTP 요청 실행
                        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                        stopWatch.stop();
                        long responseTimeMs = stopWatch.getTotalTimeMillis();
                        responseTimes.add(responseTimeMs);

                        if (response.getStatusCode().is2xxSuccessful()) {
                            successCount.incrementAndGet();
                            log.info("Request {}: completed in {} ms, Status: {}",
                                requestId, responseTimeMs, response.getStatusCode());
                        } else {
                            failureCount.incrementAndGet();
                            log.warn("Request {}: completed in {} ms, but failed with status: {}",
                                requestId, responseTimeMs, response.getStatusCode());
                        }
                    } catch (Exception e) {
                        stopWatch.stop();
                        failureCount.incrementAndGet();
                        log.error("Request {} failed after {} ms: {}",
                            requestId, stopWatch.getTotalTimeMillis(), e.getMessage());
                    }
                } finally {
                    // 2단계: 작업 완료 신호
                    phaser.arrive();
                }
            });
        }

        // 1단계: 모든 스레드가 준비되면 동시에 시작
        log.info("Main thread waiting for all threads to be ready");
        phaser.arriveAndAwaitAdvance();
        log.info("All threads started executing requests simultaneously");

        // 2단계: 모든 작업이 완료될 때까지 대기
        log.info("Main thread waiting for all requests to complete");
        phaser.arriveAndAwaitAdvance();
        log.info("All requests completed");

        totalStopWatch.stop();

        // 결과 통계 계산 및 출력
        printStatistics(totalStopWatch.getTotalTimeMillis());

        // 스레드 풀 종료
        executorService.shutdown();
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            log.warn("Executor did not terminate in the specified time.");
            executorService.shutdownNow();
        }
    }
    private static void printStatistics(long totalTimeMs) {
        // 응답 시간 통계 계산
        long sum = 0;
        long min = Long.MAX_VALUE;
        long max = 0;

        for (Long time : responseTimes) {
            sum += time;
            min = Math.min(min, time);
            max = Math.max(max, time);
        }

        double avg = responseTimes.isEmpty() ? 0 : (double) sum / responseTimes.size();

        // 결과 출력
        log.info("=== Test Results ===");
        log.info("Total execution time: {} ms", totalTimeMs);
        log.info("Successful requests: {}", successCount.get());
        log.info("Failed requests: {}", failureCount.get());
        log.info("Response time statistics:");
        log.info("  Min: {} ms", min == Long.MAX_VALUE ? "N/A" : min);
        log.info("  Max: {} ms", max);
        log.info("  Avg: {:.2f} ms", avg);
        log.info("===================");
    }


    @Test
    void getParallelOrdersByCyclicBarrier() throws BrokenBarrierException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(REQUEST_COUNT);

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();



        // CyclicBarrier 생성 (모든 스레드가 준비될 때까지 대기하기 위함)
        // REQUEST_COUNT + 1은 작업 스레드 + 메인 스레드
        CyclicBarrier barrier = new CyclicBarrier(REQUEST_COUNT + 1);

        // 전체 실행 시간 측정
        StopWatch totalStopWatch = new StopWatch();
        totalStopWatch.start();

        // 100개의 요청 생성 및 실행
        for (int i = 0; i < REQUEST_COUNT; i++) {
            executorService.execute(() -> {
                int requestId = sequence.incrementAndGet();

                try {
                    log.info("Thread {} is ready", requestId);
                    // 모든 스레드가 준비될 때까지 대기
                    barrier.await();

                    // 개별 요청 시간 측정
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();

                    // GET 요청 실행
                    String response = restTemplate.getForObject(url, String.class);

                    stopWatch.stop();
                    successCount.incrementAndGet();

                    log.info("Request {}: completed in {} seconds, Response: {}",
                        requestId, stopWatch.getTotalTimeSeconds(), response);

                } catch (Exception e) {
                    log.error("Request {} failed: {}", requestId, e.getMessage());
                }
            });
        }

        // 메인 스레드도 배리어에서 대기 (모든 작업 스레드가 준비될 때까지)
        log.info("Main thread is waiting for all worker threads to be ready");
        barrier.await();
        log.info("All threads are ready, starting concurrent requests");

        // 모든 작업이 완료될 때까지 대기
        executorService.shutdown();
        boolean completed = executorService.awaitTermination(60, TimeUnit.SECONDS);

        totalStopWatch.stop();

        log.info("Test completed: {}", completed);
        log.info("Total execution time: {} seconds", totalStopWatch.getTotalTimeSeconds());
        log.info("Success count: {}/{}", successCount.get(), REQUEST_COUNT);

        // 스레드 풀 강제 종료
        if (!executorService.isTerminated()) {
            executorService.shutdownNow();
        }
    }
}