package com.backend;

import com.backend.profile.model.Employee;
import com.backend.profile.model.SortOrder;
import com.backend.profile.repository.EmployeeRepository;
import com.backend.profile.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.backend.profile.model.SortOrder.ASCENDING;
import static com.backend.profile.model.SortOrder.DESCENDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;

    private static List<Employee> employees;

    @BeforeClass
    public static void init() throws IOException {
        employees = new ObjectMapper().readValue(
                ResourceUtils.getFile("classpath:employee.json"),
                new TypeReference<List<Employee>>() {
                });
    }

    @Test
    public void EmployeeJsonFileIsLoaded() {
        List<Employee> dbEmployees = new ArrayList<>();
        employeeRepository.findAll().forEach(dbEmployees::add);
        assertEquals(employees.size(), dbEmployees.size());
    }

    @Test
    public void EmployeesSortedBySalaryAsc() {
        Employee previous = new Employee();
        employeeService.retrieveEmployeesSortedBySalary(ASCENDING).subscribe(current -> {
            if (previous.getSalary() == null) {
                previous.setSalary(current.getSalary());
            } else {
                assertTrue(current.getSalary().compareTo(previous.getSalary()) >= 0);
            }
        });
    }

    @Test
    public void EmployeesSortedBySalaryDesc() {
        Employee previous = new Employee();
        employeeService.retrieveEmployeesSortedBySalary(DESCENDING).subscribe(current -> {
            if (previous.getSalary() == null) {
                previous.setSalary(current.getSalary());
            } else {
                assertTrue(current.getSalary().compareTo(previous.getSalary()) <= 0);
            }
        });
    }
}
