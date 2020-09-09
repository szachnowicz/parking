package com.szachnowicz.parking.presitance;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpotRepository {
    private final ParkingSpotJpaRepository jpaRepository;

    public Optional<String> getParkingSpot(String spotNumber) {
        return jpaRepository.findBySpotNumber(spotNumber).map(ParkingSpotEntity::getSpotNumber);
    }
}

interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotEntity, Long> {
    Optional<ParkingSpotEntity> findBySpotNumber(String spotNumber);

}
