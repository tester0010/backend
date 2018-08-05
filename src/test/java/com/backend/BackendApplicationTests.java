package com.backend;

import com.backend.profile.model.Employee;
import com.backend.profile.repository.EmployeeRepository;
import com.backend.profile.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.backend.profile.model.AgeFilter.*;
import static com.backend.profile.model.SortOrder.ASCENDING;
import static com.backend.profile.model.SortOrder.DESCENDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplication.class)
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
                new TypeReference<List<Employee>>() {});
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
            if (previous.getSalary() != null) {
                assertTrue(current.getSalary().compareTo(previous.getSalary()) >= 0);
            }
            previous.setSalary(current.getSalary());
        });
    }

    @Test
    public void EmployeesSortedBySalaryDesc() {
        Employee previous = new Employee();
        employeeService.retrieveEmployeesSortedBySalary(DESCENDING).subscribe(current -> {
            if (previous.getSalary() != null) {
                assertTrue(current.getSalary().compareTo(previous.getSalary()) <= 0);
            }
            previous.setSalary(current.getSalary());
        });
    }

    @Test
    public void EmployeesWithAge() {
        employeeService.retrieveEmployeesByAge(25, null)
                .subscribe(employee -> assertTrue(employee.getAge() == 25));
        employeeService.retrieveEmployeesByAge(25, EQUAL)
                .subscribe(employee -> assertTrue(employee.getAge() == 25));
    }

    @Test
    public void EmployeesBelowAge() {
        employeeService.retrieveEmployeesByAge(25, ABOVE)
                .subscribe(employee -> assertTrue(employee.getAge() > 25));
    }

    @Test
    public void EmployeesAboveAge() {
        employeeService.retrieveEmployeesByAge(25, BELOW)
                .subscribe(employee -> assertTrue(employee.getAge() < 25));
    }
}
