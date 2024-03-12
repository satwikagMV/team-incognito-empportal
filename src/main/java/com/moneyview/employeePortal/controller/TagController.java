package com.moneyview.employeePortal.controller;

import com.moneyview.employeePortal.dto.*;
import com.moneyview.employeePortal.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/individual")
    public ResponseEntity<?> getAllIndividualTag() {
        return new ResponseEntity<>(tagService.getIndividualTags(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewATag(@RequestBody TagDto newTag) {
        tagService.createOrGetTag(newTag.getName(), newTag.getType());
        return new ResponseEntity<>("Tag created successfully", HttpStatus.CREATED);
    }
}
