package com.cjavellana.adapters;

import com.cjavellana.model.AvailableEntity;
import com.cjavellana.model.Employee;
import com.cjavellana.service.EmployeeService;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataStreamingAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStreamingAdapter.class);

    @Autowired
    private EmployeeService employeeService;

    @EndpointInject(uri = "direct:kafkaRoute")
    ProducerTemplate kafkaProducer;

    public void stream(Exchange exchange, AvailableEntity availableEntity) {
        LOGGER.info("Message Received: {} {} {}",
                availableEntity.getBookCode(), availableEntity.getCountryCode(), availableEntity.getPositionDate());

        List<Employee> employees = employeeService.findAll();
        exchange.getIn().setBody(employees);

        kafkaProducer.send(exchange);
    }
}
