package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.errors.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TimeUtilsTest {

    @Test
    void shouldCorrectlyParsDate() {
        LocalDateTime localDateTime = TimeUtils.parseDate("2020-08-01 12:33");
        assertEquals(localDateTime.getYear(), 2020);
        assertEquals(localDateTime.getMonth().getValue(), 8);
        assertEquals(localDateTime.getDayOfMonth(), 1);
        assertEquals(localDateTime.getHour(), 12);
        assertEquals(localDateTime.getMinute(), 33);
    }

    @Test
    void shouldThrowBusinessExceptionForWrongFormat() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> TimeUtils.parseDate("1999/05/14 12:00:11"));
        assertEquals(businessException.getErrorCode(), "WRONG_DATE_FORMAT");
    }

    @Test
    void shouldThrowBusinessExceptionForExtendedFormat() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> TimeUtils.parseDate("2020-08-10 25:33:11"));
        assertEquals(businessException.getErrorCode(), "WRONG_DATE_FORMAT");
    }
    @Test
    void shouldThrowBusinessExceptionForShorterFormat() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> TimeUtils.parseDate("2020-08-10"));
        assertEquals(businessException.getErrorCode(), "WRONG_DATE_FORMAT");
    }

    @Test
    void newReservationBeginsInOldOne() {

        LocalDateTime outFrom = TimeUtils.parseDate("2020-08-01 12:00");
        LocalDateTime outTo = TimeUtils.parseDate("2020-08-01 14:00");

        LocalDateTime newFrom = TimeUtils.parseDate("2020-08-01 13:00");
        LocalDateTime newTo = TimeUtils.parseDate("2020-08-01 14:01");

        assertTrue(checkIfInOldReservation(outFrom, outTo,newFrom, newTo));

    }


    @Test
    void newReservationEndsInOldOne() {
        LocalDateTime outFrom = TimeUtils.parseDate("2020-08-01 21:00");
        LocalDateTime outTo = TimeUtils.parseDate("2020-08-01 22:00");

        LocalDateTime newFrom = TimeUtils.parseDate("2020-08-01 18:00");
        LocalDateTime newTo = TimeUtils.parseDate("2020-08-01 21:47");
        assertTrue(checkIfInOldReservation(outFrom, outTo,newFrom, newTo));
    }
    @Test
    void newReservationOutSideTheOldOne() {
        LocalDateTime outFrom = TimeUtils.parseDate("2020-08-01 21:00");
        LocalDateTime outTo = TimeUtils.parseDate("2020-08-01 22:00");

        LocalDateTime newFrom = TimeUtils.parseDate("2020-08-01 18:00");
        LocalDateTime newTo = TimeUtils.parseDate("2020-08-01 21:00");
        assertFalse(checkIfInOldReservation(outFrom, outTo,newFrom, newTo));

    }





    boolean checkIfInOldReservation(LocalDateTime outFrom, LocalDateTime outTo,
                                    LocalDateTime newFrom, LocalDateTime newTo) {

        return  newFrom.isAfter(outFrom) && newFrom.isBefore(outTo) ||
                newFrom.isBefore(outFrom) && newTo.isAfter(outFrom) && newTo.isBefore(outTo);

    }
}