package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoW001 {
    private int id;
    private String expense_and_income_number;
    private String expense_and_income_name;
    private BigDecimal input_money;
    private String details;
    private String radio_group_value;
    private String radio_items;
    private Date upate_time;
    private String f_name;
    private String number;
    private String new_date_Format;

}
