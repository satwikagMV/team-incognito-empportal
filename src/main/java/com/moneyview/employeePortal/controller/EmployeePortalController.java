package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.service.EmployeeService;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeePortalController {

    EmployeeService employeeService;
    TagService tagService;

    EmployeePortalController(@Autowired EmployeeService e,@Autowired TagService t){
        this.employeeService=e;
        this.tagService=t;
    }

//    @PostMapping("/signup")


}
