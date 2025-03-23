package com.ocean.scnext.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
//        System.setProperty("reactor.netty.ioWorkerCount", "1");
        SpringApplication.run(ApiApplication.class, args);
    }

}
