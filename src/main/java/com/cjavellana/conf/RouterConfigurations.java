package com.cjavellana.conf;

import com.cjavellana.adapters.DataStreamingAdapter;
import com.cjavellana.model.AvailableEntity;
import com.cjavellana.service.EmployeeService;
import com.cjavellana.typeconverters.TypeConverters;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfigurations {

    @Autowired
    private EmployeeService employeeService;

    @Bean
    public CamelContextConfiguration contextConfiguration() {
        TypeConverters typeConverters = new TypeConverters();

        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                context.getTypeConverterRegistry().addTypeConverters(typeConverters);
            }
        };
    }

    @Bean
    public RouteBuilder entityReadyRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jms:entityReady")
                        .convertBodyTo(AvailableEntity.class)
                        .bean(DataStreamingAdapter.class, "stream");
            }
        };
    }

    @Bean
    public RouteBuilder inboundRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("kafka:localvm:9092?topic=inbound&zookeeperHost=localvm&zookeeperPort=2181&groupId=group1&zookeeperSessionTimeoutMs=90000&zookeeperConnectionTimeoutMs=90000")
                        .convertBodyTo(AvailableEntity.class)
                        .bean(DataStreamingAdapter.class, "stream");
            }
        };
    }

    @Bean(name = "kafkaRouteProducer")
    public RouteBuilder kafkaProducerRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:kafkaRoute")
                        .to("kafka:192.168.1.101:9092?topic=test&zookeeperHost=localvm&zookeeperPort=2181&groupId=group1&serializerClass=kafka.serializer.StringEncoder");
            }
        };
    }
}
