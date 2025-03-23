package com.ocean.scnext.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

@Configuration
public class WebFluxConfig {
//    @Bean
//    public WebFluxConfigurer webFluxConfigurer() {
//        return new WebFluxConfigurerComposite() {
//            @Override
//            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//                configurer.defaultCodecs().maxInMemorySize(1024 * 1024);
//                // 기타 필요한 구성
//            }
//        };
//    }
}
