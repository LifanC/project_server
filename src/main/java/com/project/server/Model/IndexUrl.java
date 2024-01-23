package com.project.server.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexUrl {
    private int id;
    private String f_name;
    private String number;
    private int frequency;
    private Date upate_time;
    private String restfulApi_type;
}
