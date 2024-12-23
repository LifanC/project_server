package com.project.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class W001Bean {
    private String accountNumber;
    private String dataNumber;
    private String password;
    private String fileName;
    private MultipartFile file;
    private BigDecimal money;
    private String type;
    private String number;
    private Date update_date;
    private String update_date_format;
}
