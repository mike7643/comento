package com.demo.comentostatistic.controller;

import com.demo.comentostatistic.dto.*;
import com.demo.comentostatistic.exception.InvalidStatRequestException;
import com.demo.comentostatistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/logins/{year}")
    public ResponseEntity<YearCountDto> getYearLoginCount(@PathVariable String year){
        validateYear(year);
        return ResponseEntity.ok(statisticService.getYearLogins(year));
    }

    @GetMapping("/logins/{year}/{month}")
    public ResponseEntity<YearMonthCountDto> getYearMonthLoginCount(@PathVariable String year, @PathVariable String month){
        validateYear(year);
        validateMonth(month);
        return ResponseEntity.ok(statisticService.getYearMonthLogins(year, month));
    }

    // 1 월별 접속자 수
    @GetMapping("/stats/monthly-users")
    public ResponseEntity<List<MonthlyUserCountDto>> getMonthlyUserCnt() {
        return ResponseEntity.ok(statisticService.getMonthlyUserCnt());
    }

    // 2 일자별 접속자 수
    @GetMapping("/stats/daily-users")
    public ResponseEntity<List<DailyUserCountDto>> getDailyUserCnt() {
        return ResponseEntity.ok(statisticService.getDailyUserCnt());
    }

    // 3 평균 하루 로그인 수
    @GetMapping("/stats/avg-daily-logins")
    public ResponseEntity<AvgDailyLoginCountDto> getAvgDailyLoginCnt() {
        return ResponseEntity.ok(statisticService.getAvgDailyLoginCnt());
    }

    // 4 휴일을 제외한 로그인 수
    @GetMapping("/stats/working-day-logins")
    public ResponseEntity<WorkingDayLoginCountDto> getWorkingDayLoginCnt() {
        return ResponseEntity.ok(statisticService.getWorkingDayLoginCnt());
    }

    // 5 부서별 월별 로그인 수
    @GetMapping("/stats/dept-monthly-logins")
    public ResponseEntity<List<DeptMonthlyLoginCountDto>> getDeptMonthlyLoginCnt() {
        return ResponseEntity.ok(statisticService.getDeptMonthlyLoginCnt());
    }

    private void validateYear(String year) {
        if (year != null && !year.matches("\\d{2,4}")) {
            throw new InvalidStatRequestException("유효하지 않은 연도 형식입니다. 입력값: " + year);
        }
    }

    private void validateMonth(String month) {
        if (month != null) {
            try {
                int m = Integer.parseInt(month);
                if (m < 1 || m > 12) {
                    throw new InvalidStatRequestException("월 파라미터는 1에서 12 사이의 숫자여야 합니다. 입력값: " + month);
                }
            } catch (NumberFormatException e) {
                throw new InvalidStatRequestException("월 형식이 올바르지 않습니다. 숫자 형식으로 입력해주세요. 입력값: " + month);
            }
        }
    }
}
