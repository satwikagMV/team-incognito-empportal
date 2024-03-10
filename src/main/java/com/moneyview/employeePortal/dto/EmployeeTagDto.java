package com.moneyview.employeePortal.dto;

import com.moneyview.employeePortal.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTagDto {
    private String username;
    private String tagName;
    private Type type;
}
