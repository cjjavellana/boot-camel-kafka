package com.cjavellana;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@ComponentScan(basePackages = "com.cjavellana")
@EnableAutoConfiguration
@EnableJms
@Configuration
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(App.class, args);
        CamelSpringBootApplicationController camelSpringBootApp = appContext.getBean(CamelSpringBootApplicationController.class);
        camelSpringBootApp.blockMainThread();
    }
}
