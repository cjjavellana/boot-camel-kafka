package com.cjavellana.conf;

import com.cjavellana.model.Employee;
import com.cjavellana.service.EmployeeService;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ObjectOutputStream;
import java.util.List;

@Configuration
public class RouterConfigurations {

    @Autowired
    private EmployeeService employeeService;

    @Bean
    public KafkaEndpoint kafkaEndpoint() {
        KafkaEndpoint kafkaEndpoint = new KafkaEndpoint();
        kafkaEndpoint.setZookeeperHost("localhost");
        kafkaEndpoint.setZookeeperPort(2181);
        kafkaEndpoint.setTopic("test");
        return kafkaEndpoint;
    }

    @Bean
    public KafkaComponent kafkaComponent(KafkaEndpoint kafkaEndpoint) {
        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setEndpointClass(kafkaEndpoint.getClass());
        return kafkaComponent;
    }

    @Bean
    public RouteBuilder entityReadyRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jms:entityReady")
                        .log("${body}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                List<Employee> employees = employeeService.findAll();

                                ByteOutputStream bos = new ByteOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(employees);
                                oos.close();

                                exchange.getIn().setBody("Hello");
                                exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, 0);
                                exchange.getIn().setHeader(KafkaConstants.KEY, "1");

                                bos.close();
                            }
                        })
                        .to("kafka:192.168.1.101:9092?topic=test&zookeeperHost=localhost&zookeeperPort=2181&groupId=group1&serializerClass=kafka.serializer.StringEncoder");
            }
        };
    }

}
