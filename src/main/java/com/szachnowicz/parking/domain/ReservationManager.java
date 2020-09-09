package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.CarDto;
import com.szachnowicz.parking.dto.ReservationDto;
import com.szachnowicz.parking.dto.ReservationRequest;
import com.szachnowicz.parking.dto.errors.BusinessException;
import com.szachnowicz.parking.presitance.ParkingSpotRepository;
import com.szachnowicz.parking.presitance.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ReservationManager {
    private final ReservationRepository reservationRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final CarManager carManager;


    public ReservationDto reserveParkingSpot(ReservationRequest reservationRequest) {
        CarDto carDto = carManager.getCar(reservationRequest.getRegistrationNumber());
        String parkingSpot = parkingSpotRepository.getParkingSpot(reservationRequest.getSpotNumber()).orElseThrow(() ->
                new BusinessException("PARKING SPOT NOT EXIST"));

        ReservationDto reservationDto =
                ReservationDto.builder()
                        .registrationNumber(carDto.getRegistrationNumber())
                        .spotNumber(parkingSpot)
                        .validFrom(TimeUtils.parseDate(reservationRequest.getValidFrom()))
                        .validTo(TimeUtils.parseDate(reservationRequest.getValidTo()))
                        .build();

        validateIfSpotOccupied(reservationDto);
        validateDates(reservationDto);

        reservationRepository.saveReservation(reservationDto);
        return reservationDto;
    }

    private void validateDates(ReservationDto reservationRequest) {
        if (reservationRequest.getValidTo().isBefore(reservationRequest.getValidFrom())) {
            throw new BusinessException("INCORRECT DATES", " Valid to is before Valid form");
        }
        if (reservationRequest.getValidFrom().isBefore(LocalDateTime.now()) ||
                reservationRequest.getValidFrom().isBefore(LocalDateTime.now())) {
            throw new BusinessException("INCORRECT DATES", "Reservation date cannot be from past.");
        }
    }


    public List<ReservationDto> getAllReservationBySpotNumber(String spotNumber) {
        parkingSpotRepository.getParkingSpot(spotNumber).orElseThrow(() -> new BusinessException("PARKING SPOT NOT EXIST"));
        return reservationRepository.getAllReservationBySpotNumber(spotNumber);
    }

    private void validateIfSpotOccupied(ReservationDto reservationDto) {
        List<ReservationDto> allReservationBySpotNumber = getAllReservationBySpotNumber(reservationDto.getSpotNumber());

        LocalDateTime newFrom = reservationDto.getValidFrom();
        LocalDateTime newTo = reservationDto.getValidTo();
        // throw when duplicated
        allReservationBySpotNumber.stream()
                .filter(out-> out.getValidFrom().isEqual(newFrom) && out.getValidTo().isEqual(newTo))// only began on the same dayas a comapred
                .anyMatch(old -> {
                    throw new BusinessException("PARKING SPOT OCCUPIED", "Parking spot occupied between given dates"); });

        allReservationBySpotNumber.stream()
                .filter(res -> res.getValidFrom().getDayOfYear() == reservationDto.getValidFrom().getDayOfYear())
                .filter(out->newFrom.isAfter(out.getValidFrom()) && newFrom.isBefore(out.getValidTo()) ||
                        newFrom.isBefore(out.getValidFrom()) && newTo.isAfter(out.getValidFrom()) && newTo.isBefore(out.getValidTo()))
                .anyMatch(old -> {
                    throw new BusinessException("PARKING SPOT OCCUPIED", "Parking spot occupied between given dates"); });
    }
}
