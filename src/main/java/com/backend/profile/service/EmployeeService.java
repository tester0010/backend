package com.backend.profile.service;

import com.backend.profile.model.AgeFilter;
import com.backend.profile.model.Employee;
import com.backend.profile.model.SortOrder;
import com.backend.profile.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.backend.profile.model.AgeFilter.EQUAL;
import static java.util.Optional.of;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

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

    public Flux<Employee> retrieveEmployeesByAge(int age, AgeFilter ageFilter) {
        switch (of(ageFilter).orElse(EQUAL)) {
            case ABOVE: return Flux.fromIterable(employeeRepository.findAllByAgeGreaterThan(age));
            case BELOW: return Flux.fromIterable(employeeRepository.findAllByAgeLessThan(age));
            case EQUAL:
            default: return Flux.fromIterable(employeeRepository.findAllByAge(age));
        }
    }

    public Flux<Employee> retrieveEmployees() {
        return Flux.fromIterable(employeeRepository.findAll());
    }
}
