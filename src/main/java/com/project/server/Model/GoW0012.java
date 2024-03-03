package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoW0012 {
    private int id;
    private BigDecimal expense;
    private BigDecimal income;
    private BigDecimal totle_money;
    private String f_name;
    private String number;
    private Date upate_time;
    private String new_date_Format;

}
