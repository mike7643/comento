package com.demo.comentostatistic.service;

import com.demo.comentostatistic.dao.StatisticMapper;
import com.demo.comentostatistic.dto.YearCountDto;
import com.demo.comentostatistic.dto.YearMonthCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticMapper statisticMapper;

    public YearCountDto getYearLogins(String year) {

        return statisticMapper.selectYearLogin(year);
    }

    public YearMonthCountDto getYearMonthLogins(String year, String month) {

        return statisticMapper.selectYearMonthLogin(year + month);
    }


}