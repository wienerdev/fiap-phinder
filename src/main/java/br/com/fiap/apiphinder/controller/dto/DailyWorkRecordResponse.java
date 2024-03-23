package br.com.fiap.apiphinder.controller.dto;

import java.util.List;

public record DailyWorkRecordResponse(

        List<WorkRecordsVisualizationResponse> records
) {
}
