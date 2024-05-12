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
public class GoW002Bean {
    // c 分類_Classification_CLASSIFICATION
    // d Ex_Example_EXAMPLE
    // e 備註_Remark_REMARK
    // f 數量_quantity_QUANTITY
    // g 金額_Amount_AMOUNT
    // h 總金額_Total_TOTAL
    private int id;
    private String f_name;
    private String number;
    private String a_value;
    private String b_value;
    private String c_value;
    private String d_value;
    private String e_value;
    private BigDecimal f_value;
    private BigDecimal g_value;
    private Date new_date;
    private Date upate_time;
    private String new_date_Format;
}
