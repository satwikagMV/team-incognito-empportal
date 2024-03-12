package com.moneyview.employeePortal.controller;
// SearchController.java

import com.moneyview.employeePortal.dto.*;
import com.moneyview.employeePortal.service.EmployeeService;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final EmployeeService employeeService;
    private final TagService tagService;

    SearchController(EmployeeService employeeService, TagService tagService) {
        this.employeeService = employeeService;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<?> searchEntities(@RequestParam(value = "q", required = false) String pattern,
                                            @RequestParam(value = "e", required = false) String empPattern,
                                            @RequestParam(value = "t", required = false) String tagPattern) {
        if (pattern != null) {
            List<SearchEmpDto> empList = employeeService.getAllEmployeesMatching(pattern);
            List<TagDto> tagList = tagService.getTagsMatching(pattern);
            return new ResponseEntity<>(new List[]{empList, tagList}, HttpStatus.OK);
        } else if (empPattern != null) {
            List<SearchEmpDto> empList = employeeService.getAllEmployeesMatching(empPattern);
            return new ResponseEntity<>(empList, HttpStatus.OK);
        } else if (tagPattern != null) {
            List<TagDto> tagList = tagService.getTagsMatching(tagPattern);
            return new ResponseEntity<>(tagList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
