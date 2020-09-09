package com.szachnowicz.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class ReservationRequest {
    @Size(min = 7, max = 15, message
            = "Plate number must be at least 7 characters long")
    private String registrationNumber;
    @Size(min = 2, max = 5, message
            = "spots are in alphabetic order begin form A1")
    private String spotNumber;
    @NotNull
    @Size(min = 16, max = 16, message
            = "Date must follow this - pattern yyyy-MM-dd HH:mm")
    private String validFrom;
    @NotNull
    @Size(min = 16, max = 16, message
            = "Date must follow this - pattern yyyy-MM-dd HH:mm")
    private String validTo;

}
