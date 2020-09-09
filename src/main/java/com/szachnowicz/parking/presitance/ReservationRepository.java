package com.szachnowicz.parking.presitance;

import com.szachnowicz.parking.domain.TimeUtils;
import com.szachnowicz.parking.dto.ReservationDto;
import com.szachnowicz.parking.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationRepository {
    private final ReservationRepositoryJpa reservationRepositoryJpa;
    private final CarJpaRepository carJpaRepository;
    private final ParkingSpotJpaRepository parkingSpotJpaRepository;

    @Transactional
    public void saveReservation(ReservationDto reservationDto) {
        ReservationEntity entity = new ReservationEntity();
        CarEntity carEntity = carJpaRepository.findByRegistrationNumber(reservationDto.getRegistrationNumber()).get();
        entity.setCar(carEntity);
        entity.setCreationDate(LocalDateTime.now());
        entity.setValidFrom(reservationDto.getValidFrom());
        entity.setValidTo(reservationDto.getValidTo());
        Optional<ParkingSpotEntity> bySpotNumber = parkingSpotJpaRepository.findBySpotNumber(reservationDto.getSpotNumber());
        entity.setParkingSpot(bySpotNumber.get());
        reservationRepositoryJpa.save(entity);
    }

    public List<ReservationDto> getAllReservationBySpotNumber(String spotNo) {
        ParkingSpotEntity bySpotNumber = parkingSpotJpaRepository.findBySpotNumber(spotNo).get();
        return reservationRepositoryJpa.findAllByParkingSpot(bySpotNumber).stream().map(ReservationEntity::toDto).collect(Collectors.toList());
    }
}


interface ReservationRepositoryJpa extends JpaRepository<ReservationEntity, Long> {
    Set<ReservationEntity> findAllByParkingSpot(ParkingSpotEntity parkingSpotEntity);
}