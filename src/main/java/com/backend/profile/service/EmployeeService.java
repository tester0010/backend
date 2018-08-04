package com.backend.profile.service;

import com.backend.profile.model.Employee;
import com.backend.profile.model.SortOrder;
import com.backend.profile.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Flux<Employee> retrieveEmployeesSortedBySalary(SortOrder sortOrder) {
        switch (sortOrder) {
            case ASCENDING:
                return Flux.fromIterable(employeeRepository.findAllBySalaryAsc());
            case DESCENDING:
                return Flux.fromIterable(employeeRepository.findAllBySalaryDesc());
            default:
                return Flux.empty();
        }
    }
}
