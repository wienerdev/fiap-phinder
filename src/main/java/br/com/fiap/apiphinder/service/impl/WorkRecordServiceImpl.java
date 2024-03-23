package br.com.fiap.apiphinder.service.impl;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsVisualizationResponse;
import br.com.fiap.apiphinder.domain.entity.User;
import br.com.fiap.apiphinder.domain.entity.WorkRecord;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;
import br.com.fiap.apiphinder.domain.repository.UserRepository;
import br.com.fiap.apiphinder.domain.repository.WorkRecordRepository;
import br.com.fiap.apiphinder.helper.JwtHelper;
import br.com.fiap.apiphinder.service.WorkRecordService;
import br.com.fiap.apiphinder.utils.CommonUtils;
import br.com.fiap.apiphinder.utils.WorkRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        User user = userRepository.findByUsername(JwtHelper.extractUsername(token.substring(CommonUtils.BEARER.length())));

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        WorkRecord workRecord = new WorkRecord(LocalDateTime.now(), type, user);
        workRecordRepository.save(workRecord);
    }

    @Override
    public DailyWorkRecordResponse recordsVisualizationByToken(String token) throws Exception {
        List<WorkRecord> records = workRecordRepository.findAllByUserUsername(JwtHelper.extractUsername(token.substring(CommonUtils.BEARER.length())));
        return generateMonthlyRecordsVisualization(records);
    }

    @Override
    public DailyWorkRecordResponse recordsVisualizationByName(String username) {
        List<WorkRecord> records = workRecordRepository.findAllByUserUsername(username);
        return generateMonthlyRecordsVisualization(records);
    }

    private DailyWorkRecordResponse generateMonthlyRecordsVisualization(List<WorkRecord> records) {
        Map<LocalDate, List<WorkRecord>> recordsByDate = new TreeMap<>();

        for (WorkRecord record : records) {
            LocalDate date = record.getTimestamp().toLocalDate();
            recordsByDate.putIfAbsent(date, new ArrayList<>());
            recordsByDate.get(date).add(record);
        }

        List<WorkRecordsVisualizationResponse> aggregatedDailyRecords = new ArrayList<>();

        for (Map.Entry<LocalDate, List<WorkRecord>> entry : recordsByDate.entrySet()) {
            List<WorkRecordsVisualizationResponse> dailyRecords = WorkRecordUtils.processDailyRecords(entry);
            aggregatedDailyRecords.addAll(dailyRecords);
        }

        return new DailyWorkRecordResponse(aggregatedDailyRecords);
    }
}
