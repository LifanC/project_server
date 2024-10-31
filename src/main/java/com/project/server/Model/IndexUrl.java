package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexUrl {
    private String accountNumber;
    private String password;
    private String update_time;
    private String dataNumber;
}
