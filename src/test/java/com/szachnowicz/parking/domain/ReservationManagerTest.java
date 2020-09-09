package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.ReservationDto;
import com.szachnowicz.parking.dto.ReservationRequest;
import com.szachnowicz.parking.dto.errors.BusinessException;
import com.szachnowicz.parking.presitance.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationManagerTest {

    @Autowired
    ReservationManager reservationManager;

    @Test
    void getAllReservationForParkingSpot() {
        List<ReservationDto> list = reservationManager.getAllReservationBySpotNumber("A2");
        assertEquals(list.size(), 2);
    }


    @Test
    void shoudlThrowSpotIsNotExistingException() {
        List<ReservationDto> list = reservationManager.getAllReservationBySpotNumber("A2");
        assertEquals(list.size(), 2);
    }

    @Test
    void shouldReserveSpotWithOverlaps() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 20:00");
        reservationRequest.setValidTo("2020-09-09 21:00");

        reservationManager.reserveParkingSpot(reservationRequest);
    }
    @Test
    void shouldReserveSpot() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 00:01");
        reservationRequest.setValidTo("2020-09-09 17:59");

        reservationManager.reserveParkingSpot(reservationRequest);
    }


    @Test
    void shouldThrowSpotOccupiedException() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 19:00");
        reservationRequest.setValidTo("2020-09-09 21:00");


        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationManager.reserveParkingSpot(reservationRequest));
        assertEquals(businessException.getErrorCode(), "PARKING SPOT OCCUPIED");
    }

    @Test
    void shouldThrowSpotOccupiedExceptionWithMinuteOverlaps() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 21:01");
        reservationRequest.setValidTo("2020-09-09 21:02");


        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationManager.reserveParkingSpot(reservationRequest));
        assertEquals(businessException.getErrorCode(), "PARKING SPOT OCCUPIED");
    }

    @Test
    void shouldThrowSpotOccupiedExceptionAnotherCase() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 18:01");
        reservationRequest.setValidTo("2020-09-09 21:00");


        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationManager.reserveParkingSpot(reservationRequest));
        assertEquals(businessException.getErrorCode(), "PARKING SPOT OCCUPIED");
    }
   @Test
    void shouldThrowSpotOccupiedExceptionWhenBookedTwiceOneTheSameDate() {

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRegistrationNumber("ABCD1234");
        reservationRequest.setSpotNumber("A1");
        reservationRequest.setValidFrom("2020-09-09 21:00");
        reservationRequest.setValidTo("2020-09-09 22:00");


        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationManager.reserveParkingSpot(reservationRequest));
        assertEquals(businessException.getErrorCode(), "PARKING SPOT OCCUPIED");
    }


    @Test
    void name() {





    }
}
/**
 * <insert tableName="reservation">
 * <column name="id">183445</column>
 * <column name="car_id">1234</column>
 * <column name="parking_spot_id">1000</column>
 * <column name="creation_date">2020-09-09 18:39</column>
 * <column name="valid_from">2020-09-09 18:00</column>
 * <column name="valid_to">2020-09-09 20:00</column>
 * </insert>
 * <insert tableName="reservation">
 * <column name="id">193445</column>
 * <column name="car_id">1234</column>
 * <column name="parking_spot_id">1000</column>
 * <column name="creation_date">2020-09-09 18:39</column>
 * <column name="valid_from">2020-09-09 21:00</column>
 * <column name="valid_to">2020-09-09 22:00</column>*
 */