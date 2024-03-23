package br.com.fiap.apiphinder.service;

import br.com.fiap.apiphinder.domain.entity.WorkRecord;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;

public interface WorkRecordService {

    public WorkRecord registerWorkRecord(String token, WorkRecordType type) throws Exception;
    }
