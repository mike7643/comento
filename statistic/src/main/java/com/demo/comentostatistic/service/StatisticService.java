package com.demo.comentostatistic.service;

import com.demo.comentostatistic.dao.StatisticMapper;
import com.demo.comentostatistic.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticMapper statisticMapper;
    private final
    HolidayApiService holidayApiService;

    public YearCountDto getYearLogins(String year) {
        return statisticMapper.selectYearLogin(year);
    }

    public YearMonthCountDto getYearMonthLogins(String year, String month) {
        return statisticMapper.selectYearMonthLogin(year + month);
    }

    public List<MonthlyUserCountDto> getMonthlyUserCnt() {
        return statisticMapper.selectMonthlyUserCnt();
    }

    public List<DailyUserCountDto> getDailyUserCnt() {
        return statisticMapper.selectDailyUserCnt();
    }

    public AvgDailyLoginCountDto getAvgDailyLoginCnt() {
        return statisticMapper.selectAvgDailyLoginCnt();
    }

    public WorkingDayLoginCountDto getWorkingDayLoginCnt() {
        List<String> weekdayLogins = statisticMapper.selectWeekdayLogins();
        if (weekdayLogins.isEmpty()) return new WorkingDayLoginCountDto(0);

        Set<String> yearMonths = weekdayLogins.stream()
                .map(date -> date.substring(0, 7))
                .collect(Collectors.toSet());

        Set<String> allHolidays = new HashSet<>();
        for (String ym : yearMonths) {
            String[] split = ym.split("-");
            allHolidays.addAll(holidayApiService.getHolidays(split[0], split[1]));
        }

        long count = weekdayLogins.stream()
                .filter(date -> !allHolidays.contains(date))
                .count();

        return new WorkingDayLoginCountDto(count);
    }

    public List<DeptMonthlyLoginCountDto> getDeptMonthlyLoginCnt() {
        return statisticMapper.selectDeptMonthlyLoginCnt();
    }
}
