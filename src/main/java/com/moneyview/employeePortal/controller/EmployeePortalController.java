package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.AuthRequest;
import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.service.EmployeeService;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class EmployeePortalController {

    EmployeeService employeeService;
    TagService tagService;

    EmployeePortalController(EmployeeService e,TagService t){
        this.employeeService=e;
        this.tagService=t;
    }

    @PostMapping("/signup")
    public ResponseEntity<?>  signupEmployee(@RequestBody EmployeeRequest empData){
        try {
            employeeService.createEmployee(empData);
        }
        catch (Throwable e){
            new ResponseEntity<>("Already Exists",HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Employee Created",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee (@RequestBody AuthRequest authReq){
        try {
            Boolean success =employeeService.verify(authReq.getUsername(),authReq.getPassword());
            if (!success) return new ResponseEntity<>(false,HttpStatus.UNAUTHORIZED);
        }
        catch (Throwable e){
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(true,HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> employeeDetails(@PathVariable String username){
        return new ResponseEntity<EmployeeDto>(employeeService.getEmployeeDetails(username), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDisplayImage(EmployeeRequest req){
        String displayImgUrl= employeeService.addOrUpdateDisplayImage(req);
        if (displayImgUrl!=null){
            return new ResponseEntity<>(displayImgUrl,HttpStatus.OK);
        }

        return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
