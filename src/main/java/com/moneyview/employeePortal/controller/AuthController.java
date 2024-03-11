package com.moneyview.employeePortal.controller;


import com.moneyview.employeePortal.dto.AuthRequest;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class AuthController {

    private final EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupEmployee(@RequestBody EmployeeRequest empData){
        try {
            employeeService.createEmployee(empData);
        }
        catch (Throwable e){
            new ResponseEntity<>("Already Exists", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Employee Created",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee (@RequestBody AuthRequest authReq){
        try {
            System.out.println(authReq.getUsername()+" "+authReq.getPassword());
            Boolean success =employeeService.verifyCredentials(authReq.getUsername(),authReq.getPassword());

            if (!success) return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }
        catch (Throwable e){
            return new ResponseEntity<>("No User Found",HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(true,HttpStatus.OK);
    }
}
