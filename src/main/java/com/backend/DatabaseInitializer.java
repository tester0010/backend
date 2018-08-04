package com.backend;

import com.backend.profile.repository.EmployeeRepository;
import com.backend.profile.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.List;

@Component
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Employee> employees = new ObjectMapper().readValue(
                ResourceUtils.getFile("classpath:employee.json"),
                new TypeReference<List<Employee>>() {
                });
        employees.forEach(employee -> employeeRepository.save(employee));
    }
}
