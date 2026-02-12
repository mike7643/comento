package com.demo.comentostatistic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptMonthlyLoginCountDto {
    private String department;
    private String month;
    private int loginCount;
}
