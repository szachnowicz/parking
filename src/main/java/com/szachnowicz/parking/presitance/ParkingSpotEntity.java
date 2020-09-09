package com.szachnowicz.parking.presitance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "parking_spot")
@NoArgsConstructor
@Getter
class ParkingSpotEntity extends BaseEntity {
    private String spotNumber;

}
