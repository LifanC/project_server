package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class A {
    private Integer a_id;
    private String date;
    private String expense_and_income_number;
    private Integer inputMoney;
    private String details;
    private String expense_and_income_name;
}
