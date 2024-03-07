package com.moneyview.employeePortal.dto;

import com.moneyview.employeePortal.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private String name;
    private Type type;
    private Set<EmployeeDto> associatedEmployees;
}
