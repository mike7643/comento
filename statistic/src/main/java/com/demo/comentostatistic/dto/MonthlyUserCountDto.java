package com.demo.comentostatistic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyUserCountDto {
    private String month;
    private int userCount;
}
