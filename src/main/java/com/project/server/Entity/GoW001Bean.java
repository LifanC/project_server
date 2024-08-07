package com.project.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoW001Bean {
    private int id;
    private String expense_and_income_number;
    private BigDecimal input_money;
    private BigDecimal setInputMoney ;
    private String details;
    private Date new_date;
    private String radio_group_value;
    private String radio_items;
    private Date upate_time;
    private String f_name;
    private String number;
    private String new_date_Format;
}
