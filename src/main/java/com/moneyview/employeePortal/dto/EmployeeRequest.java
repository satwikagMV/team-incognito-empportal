package com.moneyview.employeePortal.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String username=null;
    private String email=null;
    private String password=null;
    private String name=null;
    private String designation=null;
    private Integer level=null;
    private String phoneNo=null;
    private String slackId=null;
    private MultipartFile badgeImg=null;
    private  MultipartFile displayImg=null;
    private String managerUsername=null;
}
