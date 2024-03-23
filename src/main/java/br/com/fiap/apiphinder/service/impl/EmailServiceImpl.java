package br.com.fiap.apiphinder.service.impl;

import br.com.fiap.apiphinder.controller.dto.DailyWorkRecordResponse;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsIntervals;
import br.com.fiap.apiphinder.controller.dto.WorkRecordsVisualizationResponse;
import br.com.fiap.apiphinder.domain.entity.User;
import br.com.fiap.apiphinder.domain.repository.UserRepository;
import br.com.fiap.apiphinder.helper.JwtHelper;
import br.com.fiap.apiphinder.service.EmailService;
import br.com.fiap.apiphinder.service.WorkRecordService;
import br.com.fiap.apiphinder.utils.CommonUtils;
import br.com.fiap.apiphinder.utils.WorkRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkRecordService workRecordService;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendWorkRecordEmail(String token) throws Exception {
        User user = userRepository.findByUsername(JwtHelper.extractUsername(token.substring(CommonUtils.BEARER.length())));

        DailyWorkRecordResponse allRecords = workRecordService.recordsVisualizationByName(user.getUsername());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject(CommonUtils.EMAIL_FREQUENCY_SUBJECT);
        message.setText(buildWorkRecordsEmailContent(WorkRecordUtils.getPreviousMonthNameInPortuguese(), allRecords.records()));
        javaMailSender.send(message);
    }

    public String buildWorkRecordsEmailContent(String monthName, List<WorkRecordsVisualizationResponse> records) {
        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_TITLE).append(monthName).append(")\n\n");

        for (WorkRecordsVisualizationResponse record : records) {
            LocalDate date = record.date();
            LocalTime startTime = record.startWorkTime();
            List<WorkRecordsIntervals> intervals = record.intervals();
            LocalTime endTime = record.endWorkTime();
            String totalWorkedHours = record.getTotalWorkedHours();

            sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_DATE).append(date.format(DATE_FORMAT)).append("\n");
            sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_ENTRY_TIME).append(startTime.format(TIME_FORMAT)).append("\n");

            sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_INTERVAL);
            for (WorkRecordsIntervals interval : intervals) {
                LocalTime startInterval = interval.startTime();
                LocalTime endInterval = interval.endTime();
                sb.append(startInterval.format(TIME_FORMAT)).append(" - ").append(endInterval.format(TIME_FORMAT));
            }
            sb.append("\n");

            sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_END_WORK_TIME).append(endTime.format(TIME_FORMAT)).append("\n");
            sb.append(CommonUtils.EMAIL_FREQUENCY_BODY_TOTAL_WORKED_HOURS).append(totalWorkedHours).append("\n\n");
        }

        return sb.toString();
    }
}
