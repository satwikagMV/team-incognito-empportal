package com.moneyview.employeePortal.dto;


import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.entity.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String email;

    private String username;

    private String name;

    private String designation;

    private Integer level;

    private String phoneNo;

    private String slackId=null;

    private String displayImgUrl=null;

    private String badgeImgUrl=null;

    private Employee manager=null;

    private Set<Employee> reportee=new HashSet<>();

    private Set<Tag> assignedTags=new HashSet<>();

}
