package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoW002 {
    // m_code_M_CODE
    // 數量_Quantity_QUANTITY
    // 金額_Amount_AMOUNT
    // 總金額_Total_TOTAL
    private int id;
    private String m_code;
    private String remark;
    private String quantity;
    private String amount;
    private String total;

}
