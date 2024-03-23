package br.com.fiap.apiphinder.controller;

import br.com.fiap.apiphinder.service.EmailService;
import br.com.fiap.apiphinder.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/record/monthly")
    public ResponseEntity<String> generateMonthlyRecordsReportByUser(
            @RequestHeader(name = CommonUtils.HEADER_AUTHORIZATION) String token) throws Exception {
        emailService.sendWorkRecordEmail(token);
        return new ResponseEntity<>("Email enviado com sucesso!", HttpStatus.OK);
    }

}
