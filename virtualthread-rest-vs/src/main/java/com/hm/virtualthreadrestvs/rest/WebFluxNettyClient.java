package com.hm.virtualthreadrestvs.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/5/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class WebFluxNettyClient {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(
                GET("/get"),
                request -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(Mono.just("ok").delayElement(Duration.ofMillis(300)), String.class))
        );
    }
}