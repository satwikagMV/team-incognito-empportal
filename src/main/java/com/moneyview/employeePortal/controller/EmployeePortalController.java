package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.*;
import com.moneyview.employeePortal.service.EmployeeService;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*",originPatterns = "*")
@RestController
@RequestMapping("/api")
public class EmployeePortalController {

    private final EmployeeService employeeService;
    private final TagService tagService;

    EmployeePortalController(EmployeeService e,TagService t){
        this.employeeService=e;
        this.tagService=t;
    }



    @GetMapping("/reportee/{username}")
    public ResponseEntity<?> getReportee(@PathVariable String username){
        return new ResponseEntity<>(employeeService.getReportee(username),HttpStatus.OK);

    }

    @GetMapping("/manager/{username}")
    public ResponseEntity<?> getManager(@PathVariable String username){
        EmployeeDto manager=employeeService.getManagerDetails(username);
        if (manager==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(employeeService.getManagerDetails(username),HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEntities(@RequestParam(value = "q",required = false) String pattern,
                                            @RequestParam(value = "e",required = false) String empPattern,
                                            @RequestParam(value = "t",required = false) String tagPattern){
        if (pattern!=null){
            List<SearchEmpDto> empList = employeeService.getAllEmployeesMatching(pattern);
            List<TagDto> tagList=tagService.getTagsMatching(pattern);
            return new ResponseEntity<>(new List[]{empList, tagList},HttpStatus.OK);
        }
        else if (empPattern!=null){
            List<SearchEmpDto> empList = employeeService.getAllEmployeesMatching(empPattern);
            return new ResponseEntity<>(empList,HttpStatus.OK);
        }
        else if (tagPattern!=null) {
            List<TagDto> tagList=tagService.getTagsMatching(tagPattern);
            return new ResponseEntity<>(tagList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<?> employeeDetails(@PathVariable String username){
        return new ResponseEntity<>(employeeService.getEmployeeDetails(username), HttpStatus.OK);
    }

    @PostMapping("/assign")
    public  ResponseEntity<?> assignEmployeeTag(@RequestBody EmployeeTagDto empTag){
        employeeService.assignTag(empTag.getUsername(), empTag.getTagName(),empTag.getType());
        return new ResponseEntity<>("Added Successfully",HttpStatus.CREATED);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDisplayImage(EmployeeRequest req){
        String displayImgUrl= employeeService.addOrUpdateDisplayImageCloudinary(req);
        if (displayImgUrl!=null){
            return new ResponseEntity<>(displayImgUrl,HttpStatus.OK);
        }

        return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/individual-tag")
    public ResponseEntity<?> getAllIndividualTag(){
        return new ResponseEntity<>(tagService.getIndividualTags(),HttpStatus.OK);
    }

    @PostMapping("/create-tag")
    public ResponseEntity<?> createNewATag(@RequestBody TagDto newTag){
        tagService.createOrGetTag(newTag.getName(),newTag.getType());
        return new ResponseEntity<>("Tag Created Successfully",HttpStatus.CREATED);
    }

}
