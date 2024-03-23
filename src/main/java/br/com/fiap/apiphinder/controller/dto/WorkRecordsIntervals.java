package br.com.fiap.apiphinder.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record WorkRecordsIntervals(
        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime startTime,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime endTime
) {


}
