package br.com.fiap.apiphinder.service;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;

import java.util.List;

public interface WorkRecordService {

    void registerWorkRecord(String token, WorkRecordType type) throws Exception;
    List<DailyWorkRecordResponse> recordsVisualization(String token) throws Exception;

    }
