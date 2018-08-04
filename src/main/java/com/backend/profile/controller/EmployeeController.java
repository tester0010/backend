package com.backend.profile.controller;

import com.backend.profile.model.Employee;
import com.backend.profile.model.SortOrder;
import com.backend.profile.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("employees/bySalary/{sortOrder}")
    public Flux<Employee> retrieveEmployeesSortedBySalary(@PathVariable SortOrder sortOrder) {
        return this.employeeService.retrieveEmployeesSortedBySalary(sortOrder);
    }
}
