package com.szachnowicz.parking.presitance;

import com.szachnowicz.parking.dto.ReservationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity(name = "reservation")
@NoArgsConstructor
@Getter
@Setter
class ReservationEntity extends BaseEntity {
    @OneToOne
    private CarEntity car;
    @OneToOne
    private ParkingSpotEntity parkingSpot;
    private LocalDateTime creationDate;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;


    public ReservationDto toDto() {
        return ReservationDto
                .builder()
                .validTo(validTo)
                .validFrom(validFrom)
                .registrationNumber(car.getRegistrationNumber())
                .spotNumber(parkingSpot.getSpotNumber())
                .build();
    }
}
