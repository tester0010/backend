package com.backend.profile.repository;

import com.backend.profile.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("FROM Employee ORDER BY salary asc")
    Iterable<Employee> findAllBySalaryAsc();

    @Query("FROM Employee ORDER BY salary desc")
    Iterable<Employee> findAllBySalaryDesc();
}
