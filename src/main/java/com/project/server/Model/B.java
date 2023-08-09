package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B {
    private Integer b_id;
    private String date;
    private Integer expense;
    private Integer income;
    private Integer totleMoney;
}
