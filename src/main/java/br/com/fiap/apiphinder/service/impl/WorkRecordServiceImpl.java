package br.com.fiap.apiphinder.service.impl;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsIntervals;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsVisualizationResponse;
import br.com.fiap.apiphinder.domain.entity.User;
import br.com.fiap.apiphinder.domain.entity.WorkRecord;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;
import br.com.fiap.apiphinder.domain.repository.UserRepository;
import br.com.fiap.apiphinder.domain.repository.WorkRecordRepository;
import br.com.fiap.apiphinder.helper.JwtHelper;
import br.com.fiap.apiphinder.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void registerWorkRecord(String token, WorkRecordType type) throws Exception {
        User user = userRepository.findByUsername(JwtHelper.extractUsername(token.substring("Bearer ".length())));

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        WorkRecord workRecord = new WorkRecord(LocalDateTime.now(), type, user);
        workRecordRepository.save(workRecord);
    }

    @Override
    public List<DailyWorkRecordResponse> recordsVisualization(String token) throws Exception {
        List<WorkRecord> records = workRecordRepository.findAllByUserUsername(JwtHelper.extractUsername(token.substring("Bearer ".length())));

        Map<LocalDate, List<WorkRecord>> recordsByDate = new TreeMap<>();

        for (WorkRecord record : records) {
            LocalDate date = record.getTimestamp().toLocalDate();
            recordsByDate.putIfAbsent(date, new ArrayList<>());
            recordsByDate.get(date).add(record);
        }

        List<DailyWorkRecordResponse> responseList = new ArrayList<>();

        for (Map.Entry<LocalDate, List<WorkRecord>> entry : recordsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<WorkRecordsVisualizationResponse> dailyRecords = processDailyRecords(entry.getValue());
            responseList.add(new DailyWorkRecordResponse(date, dailyRecords));
        }

        return responseList;
    }

    private List<WorkRecordsVisualizationResponse> processDailyRecords(List<WorkRecord> dailyRecords) {
        List<WorkRecordsVisualizationResponse> responseList = new ArrayList<>();

        LocalTime startTime = null;
        LocalTime endTime = null;
        List<LocalTime[]> intervals = new ArrayList<>();
        LocalTime intervalStart = null;

        for (WorkRecord record : dailyRecords) {
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
                    startTime,
                    workRecordsIntervals,
                    endTime,
                    totalWorked);
            responseList.add(response);
        }

        return responseList;
    }

    private Duration calculateTotalWorked(LocalTime start, LocalTime end, List<LocalTime[]> intervals) {
        if (start == null || end == null) return Duration.ZERO;

        Duration total = Duration.between(start, end);

        for (LocalTime[] interval : intervals) {
            Duration breakDuration = Duration.between(interval[0], interval[1]);
            total = total.minus(breakDuration);
        }

        return total;
    }
}
