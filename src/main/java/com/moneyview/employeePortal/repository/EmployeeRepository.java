package com.moneyview.employeePortal.repository;

import com.moneyview.employeePortal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findOneByEmail(String email);
    Employee findOneByUsername(String username);

    List<Employee> findByNameLike(String pattern);

}
