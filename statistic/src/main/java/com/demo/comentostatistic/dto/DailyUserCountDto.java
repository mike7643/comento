package com.demo.comentostatistic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyUserCountDto {
    private String date;
    private int userCount;
}
