package com.project.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoW0012Bean {
    private int id;
    private Date new_date;
    private BigDecimal expense;
    private BigDecimal income;
    private BigDecimal totle_money;
    private String f_name;
    private String number;
    private Date upate_time;
    private String new_date_Format;

}
