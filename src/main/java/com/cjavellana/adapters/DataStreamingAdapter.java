package com.cjavellana.adapters;

import com.cjavellana.constants.Constants;
import com.cjavellana.model.AvailableEntity;
import com.cjavellana.model.Employee;
import com.cjavellana.service.EmployeeService;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DataStreamingAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStreamingAdapter.class);

    @Autowired
    private EmployeeService employeeService;

    @EndpointInject(uri = "direct:kafkaRoute")
    private ProducerTemplate kafkaProducer;

    public void stream(Exchange exchange, AvailableEntity availableEntity) {
        LOGGER.info("Message Received: {} {} {}",
                availableEntity.getBookCode(), availableEntity.getCountryCode(), availableEntity.getPositionDate());

        int pageNo = 0;
        PageRequest p = new PageRequest(pageNo, Constants.PAGE_SIZE);
        Page<Employee> employees = employeeService.findAll(p);

        while (pageNo <= employees.getTotalPages()) {

            BlockingQueue<Employee> queue = new ArrayBlockingQueue<>(employees.getSize(), true, employees.getContent());
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            StringBuffer csvBuffer = new StringBuffer();
            while (!queue.isEmpty()) {
                executorService.submit(() -> {
                    try {
                        Employee e = queue.take();
                        csvBuffer.append(e.toCSV());

                    } catch (InterruptedException ie) {
                        LOGGER.warn("Unable to convert to CSV", ie);
                    }
                });
            }

            LOGGER.info("Sending {} to {} Topic", csvBuffer.toString(), availableEntity.getTable());

            exchange.getIn().setBody(csvBuffer.toString(), String.class);
            kafkaProducer.send(exchange);

            //kafkaProducer.sendBody("direct:kafka" + availableEntity.getTable() + "Route", csvBuffer.toString());

            pageNo++;
            p = new PageRequest(pageNo, Constants.PAGE_SIZE);
            employees = employeeService.findAll(p);
        }
    }
}
