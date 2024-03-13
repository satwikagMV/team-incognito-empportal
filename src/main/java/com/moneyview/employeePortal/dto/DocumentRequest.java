package com.moneyview.employeePortal.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    private MultipartFile document;
    private String fileName;
    private String username;
}
