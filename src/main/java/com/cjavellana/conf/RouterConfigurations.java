package com.cjavellana.conf;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfigurations {

    @Bean
    public RouteBuilder entityReadyRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jms:entityReady").log("${body}");
            }
        };
    }

}
