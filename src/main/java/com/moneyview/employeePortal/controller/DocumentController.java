package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/upload")
public class DocumentController {
    private final EmployeeService employeeService;

    DocumentController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/display")
    public ResponseEntity<?> uploadDisplayImage(EmployeeRequest req){
        String displayImgUrl= employeeService.addOrUpdateDisplayImageFirebase(req);
        if (displayImgUrl!=null){
            return new ResponseEntity<>(displayImgUrl,HttpStatus.OK);
        }

        return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/docs")
    public ResponseEntity<?> uploadUserDocuments(@RequestBody EmployeeRequest req){
        return new ResponseEntity<>("Hello",HttpStatus.CREATED);
    }
}
