package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.service.EmployeeService;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeePortalController {

    EmployeeService employeeService;
    TagService tagService;

    EmployeePortalController(EmployeeService e,TagService t){
        this.employeeService=e;
        this.tagService=t;
    }

//    @PostMapping("/signup")

    @GetMapping("/user/{username}")
    public EmployeeDto employeeDetails(@PathVariable String username){
        return employeeService.getEmployeeDetails(username);
    }




}
