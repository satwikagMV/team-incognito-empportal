package com.moneyview.employeePortal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private  String username;
    private String email;
    private String name;
    private String designation;
    private Integer level;
    private String displayImgUrl;

}
