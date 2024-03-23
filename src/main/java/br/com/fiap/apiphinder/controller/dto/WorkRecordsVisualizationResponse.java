package br.com.fiap.apiphinder.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record WorkRecordsVisualizationResponse(

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime startWorkTime,
        List<WorkRecordsIntervals> intervals,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime endWorkTime,

        @JsonIgnore
        Duration totalWorked
) {

    @JsonProperty("totalWorkedHours")
    public String getTotalWorkedHours() {
        return String.format("%02d:%02d:%02d", totalWorked.toHours(), totalWorked.toMinutesPart(), totalWorked.toSecondsPart());
    }
}
