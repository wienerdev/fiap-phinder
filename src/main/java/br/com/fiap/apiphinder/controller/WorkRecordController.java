package br.com.fiap.apiphinder.controller;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.domain.enums.WorkRecordType;
import br.com.fiap.apiphinder.service.WorkRecordService;
import br.com.fiap.apiphinder.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/record")
public class WorkRecordController {

    @Autowired
    private WorkRecordService workRecordService;

    @PostMapping
    public ResponseEntity<String> workRecordByType(
            @RequestHeader(name = CommonUtils.HEADER_AUTHORIZATION) String token, @RequestParam WorkRecordType type) throws Exception {
        workRecordService.registerWorkRecord(token, type);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<DailyWorkRecordResponse> getWorkRecordsByUser(@RequestHeader(name = CommonUtils.HEADER_AUTHORIZATION) String token) throws Exception {
        return new ResponseEntity<>(workRecordService.recordsVisualizationByToken(token), HttpStatus.OK);
    }

}
