package com.project.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexUrlBean {
    private String accountNumber;
    private String password;
    private int dialogValue;
    private String update_time;
    private String dataNumber;
}