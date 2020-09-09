package com.szachnowicz.parking.presitance;

import com.szachnowicz.parking.dto.CarDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "car")
@NoArgsConstructor
@Getter
@Setter
class CarEntity extends BaseEntity {
    private String registrationNumber;
    private String brand;
    private String model;

    public CarDto toDto() {
        return new CarDto(registrationNumber, model, brand);
    }
}
