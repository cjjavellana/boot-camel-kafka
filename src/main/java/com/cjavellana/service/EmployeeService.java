package com.cjavellana.service;

import com.cjavellana.model.Employee;
import com.cjavellana.repositories.EmployeeRepository;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }
}
