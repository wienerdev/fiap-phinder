package br.com.fiap.apiphinder.service.impl;

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

import java.time.LocalDateTime;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public WorkRecord registerWorkRecord(String token, WorkRecordType type) throws Exception {
        User user = userRepository.findByUsername(JwtHelper.extractUsername(token.substring("Bearer ".length())));

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        WorkRecord workRecord = new WorkRecord(LocalDateTime.now(), type, user);
        return workRecordRepository.save(workRecord);
    }
}
