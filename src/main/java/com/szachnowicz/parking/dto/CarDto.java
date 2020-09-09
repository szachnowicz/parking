package com.szachnowicz.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDto {
    @NotNull
    @Size(min = 7, max = 15, message
            = "Plate number must be at least 7 characters long")
    private String registrationNumber;
    @NotNull
    private String model;
    @NotNull
    private String brand;
}
