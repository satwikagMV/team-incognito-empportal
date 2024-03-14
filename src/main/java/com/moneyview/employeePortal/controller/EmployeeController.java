// EmployeeController.java

package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.*;
import com.moneyview.employeePortal.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/user")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/reportee/{username}")
    public ResponseEntity<?> getReportee(@PathVariable String username) {
        return new ResponseEntity<>(employeeService.getReportee(username), HttpStatus.OK);
    }

    @GetMapping("/manager/{username}")
    public ResponseEntity<?> getManager(@PathVariable String username) {
        EmployeeDto manager = employeeService.getManagerDetails(username);
        if (manager == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable String username) {
        return new ResponseEntity<>(employeeService.getEmployeeDetails(username), HttpStatus.OK);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignEmployeeTag(@RequestBody EmployeeTagDto empTag) {
        employeeService.assignTag(empTag.getUsername(), empTag.getTagName(), empTag.getType());
        return new ResponseEntity<>("Tag assigned successfully", HttpStatus.CREATED);
    }

    @PostMapping("/unassign")
    public ResponseEntity<?> unAssignEmployeeTag(@RequestBody EmployeeTagDto empTag) {
        employeeService.unAssignTag(empTag.getUsername(), empTag.getTagName(), empTag.getType());
        return new ResponseEntity<>("Tag unassigned successfully", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateEmployee(EmployeeRequest req){
        try {
            employeeService.updateEmployeeDetails(req);
        } catch (IOException e) {
            return new ResponseEntity<>("Unable to Update", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }
}

