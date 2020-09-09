package com.szachnowicz.parking.contoller;

import com.szachnowicz.parking.domain.ReservationManager;
import com.szachnowicz.parking.dto.ReservationDto;
import com.szachnowicz.parking.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor
class ReservationController {
    private final ReservationManager reservationManager;

    @PostMapping
    ReservationDto reserveParkingSpot(@RequestBody @Valid ReservationRequest reservationRequest) {
        return reservationManager.reserveParkingSpot(reservationRequest);
    }

    @GetMapping("/{spotNumber}")
    List<ReservationDto> allReservationByParkingSpot(@PathVariable @Valid String spotNumber) {
        return reservationManager.getAllReservationBySpotNumber(spotNumber);
    }


}
