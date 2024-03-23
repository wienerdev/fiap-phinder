package br.com.fiap.apiphinder.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record DailyWorkRecordResponse(
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,
        List<WorkRecordsVisualizationResponse> records
) {
}
