package br.com.fiap.apiphinder.service;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;

public interface WorkRecordService {

    void registerWorkRecord(String token, WorkRecordType type) throws Exception;
    DailyWorkRecordResponse recordsVisualizationByToken(String token) throws Exception;
    DailyWorkRecordResponse recordsVisualizationByName(String username) throws Exception;


}
