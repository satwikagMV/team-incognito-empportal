package com.moneyview.employeePortal.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String designation;
    private Integer level;
    private String phoneNo;
    private String slackId;
    private MultipartFile badgeImg;
    private  MultipartFile displayImg;
    private String managerUsername;
}
