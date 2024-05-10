package com.project.server.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexUrlBean {
    private int id;
    private String f_name;
    private String number;
    private Date upate_time;
    private String restfulApi_type;
    private String permissions_value;
}
