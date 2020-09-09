package com.szachnowicz.parking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;



@Getter
@Builder
public class ReservationDto {
    @Size(min = 7, max = 15, message
            = "Plate number must be at least 7 characters long")
    private final String registrationNumber;
    @Size(min = 2, max = 5, message
            = "spots are in alphabetic order begin form A1")
    private final String spotNumber;
    @NotNull
    private final LocalDateTime validFrom;
    @NotNull
    private final LocalDateTime validTo;

}
