package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.errors.BusinessException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class TimeUtils {
    public static LocalDateTime parseDate(String date) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, format);
        } catch (DateTimeParseException timeParseException) {
            timeParseException.printStackTrace();
            throw new BusinessException("WRONG_DATE_FORMAT", "Wrong date format please, fallow this - 'yyyy-MM-dd HH:mm' pattern");
        }
    }
}
