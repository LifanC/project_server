package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoW002 {
    private int id;
    private String a_value;
    private String b_value;
    private String c_value;
    private String d_value;
    private String e_value;
    private BigDecimal f_value;
    private BigDecimal g_value;
    private BigDecimal h_value;
}
