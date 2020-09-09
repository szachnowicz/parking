package com.szachnowicz.parking.contoller;

import com.szachnowicz.parking.domain.CarManager;
import com.szachnowicz.parking.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
class CarController {

    private final CarManager carManager;

    @GetMapping("/{registrationNumber}")
    public CarDto getCar(@PathVariable String registrationNumber) {
        return carManager.getCar(registrationNumber);
    }

    @PostMapping
    public void createCar(@RequestBody @Valid CarDto carDto) {
        carManager.createCar(carDto);
    }

    @PutMapping
    public void updateCar(@RequestBody @Valid CarDto carDto) {
        carManager.updateCar(carDto);
    }

    @DeleteMapping("/{registrationNumber}")
    public void deleteCar(@PathVariable String registrationNumber) {
        carManager.deleteCar(registrationNumber);
    }
}





