package com.moneyview.employeePortal.service;

import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;

    public Boolean verifyCredentials(String username, String password) throws Throwable {
        Employee emp = employeeRepository.findOneByUsername(username);
        if (emp == null) throw new NullPointerException();
        return Objects.equals(emp.getPassword(), password);
    }
}