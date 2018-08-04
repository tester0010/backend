package com.backend;

import com.backend.profile.data.EmployeeRepository;
import com.backend.profile.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void EmployeeJsonFileIsLoaded() throws IOException {
        List<Employee> employees = new ObjectMapper().readValue(
                ResourceUtils.getFile("classpath:employee.json"),
                new TypeReference<List<Employee>>() {
                });
        List<Employee> dbEmployees = new ArrayList<>();
        employeeRepository.findAll().forEach(dbEmployees::add);
        Assert.assertEquals(employees.size(), dbEmployees.size());
    }

}
