package br.com.fiap.apiphinder.utils;

import br.com.fiap.apiphinder.controller.dto.WorkRecordsIntervals;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsVisualizationResponse;
import br.com.fiap.apiphinder.domain.entity.WorkRecord;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkRecordUtils {

    public static List<WorkRecordsVisualizationResponse> processDailyRecords(Map.Entry<LocalDate, List<WorkRecord>> dailyRecords) {
        List<WorkRecordsVisualizationResponse> responseList = new ArrayList<>();

        LocalTime startTime = null;
        LocalTime endTime = null;
        List<LocalTime[]> intervals = new ArrayList<>();
        LocalTime intervalStart = null;

        for (WorkRecord record : dailyRecords.getValue()) {
            LocalTime time = record.getTimestamp().toLocalTime();

            switch (record.getType()) {
                case ENTRADA -> {
                    if (startTime == null) {
                        startTime = time;
                    }
                }
                case INTERVALO_INICIO -> intervalStart = time;
                case INTERVALO_FIM -> {
                    if (intervalStart != null) {
                        intervals.add(new LocalTime[]{intervalStart, time});
                        intervalStart = null;
                    }
                }
                case SAIDA ->
                        endTime = time;
            }
        }

        Duration totalWorked = calculateTotalWorked(startTime, endTime, intervals);

        List<WorkRecordsIntervals> workRecordsIntervals = new ArrayList<>();

        for (LocalTime[] interval : intervals) {
            workRecordsIntervals.add(new WorkRecordsIntervals(interval[0], interval[1]));
        }

        if (startTime != null) {
            WorkRecordsVisualizationResponse response = new WorkRecordsVisualizationResponse(
                    dailyRecords.getKey(),
                    startTime,
                    workRecordsIntervals,
                    endTime,
                    totalWorked);
            responseList.add(response);
        }

        return responseList;
    }

    public static Duration calculateTotalWorked(LocalTime start, LocalTime end, List<LocalTime[]> intervals) {
        return getDuration(start, end, intervals);
    }

    public static Duration getDuration(LocalTime start, LocalTime end, List<LocalTime[]> intervals) {
        if (start == null || end == null) return Duration.ZERO;

        Duration total = Duration.between(start, end);

        for (LocalTime[] interval : intervals) {
            Duration breakDuration = Duration.between(interval[0], interval[1]);
            total = total.minus(breakDuration);
        }

        return total;
    }

    public static String getPreviousMonthNameInPortuguese() {
        LocalDate firstDayOfCurrentMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastMonth = firstDayOfCurrentMonth.minusDays(1);
        Locale ptBr = new Locale("pt", "BR");

        String monthName = new DateFormatSymbols(ptBr).getMonths()[lastMonth.getMonthValue() - 1];

        return monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
    }
}
