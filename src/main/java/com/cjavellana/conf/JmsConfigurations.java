package com.cjavellana.conf;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app-config.properties")
public class JmsConfigurations {

    @Value("${activemq.url}")
    private String activeMQUrl;

    @Bean
    public JmsComponent jms() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL(activeMQUrl);
        return activeMQComponent;
    }

}
