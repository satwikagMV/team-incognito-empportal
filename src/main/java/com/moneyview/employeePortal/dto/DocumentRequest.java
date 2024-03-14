package com.moneyview.employeePortal.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    private MultipartFile file=null;
    private String fileName;
    private String username;
}
