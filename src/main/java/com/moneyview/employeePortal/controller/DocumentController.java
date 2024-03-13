package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.DocumentRequest;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public ResponseEntity<?> uploadUserDocuments(@RequestBody DocumentRequest req){
        String docsUrl= null;
        try {
            docsUrl = employeeService.uploadDocument(req);
        } catch (IOException e) {
           return new ResponseEntity<>("Upload Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(docsUrl,HttpStatus.CREATED);
    }
}
