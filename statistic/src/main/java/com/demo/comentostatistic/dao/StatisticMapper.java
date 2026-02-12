package com.demo.comentostatistic.dao;

import com.demo.comentostatistic.dto.*;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    YearCountDto selectYearLogin(String year);
    YearMonthCountDto selectYearMonthLogin(String yearMonth);

    List<MonthlyUserCountDto> selectMonthlyUserCnt();
    List<DailyUserCountDto> selectDailyUserCnt();
    AvgDailyLoginCountDto selectAvgDailyLoginCnt();
    List<String> selectWeekdayLogins();
    List<DeptMonthlyLoginCountDto> selectDeptMonthlyLoginCnt();
}
