package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.CarDto;
import com.szachnowicz.parking.dto.errors.BusinessException;
import com.szachnowicz.parking.presitance.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CarManager {
    private final Supplier<BusinessException> throwCarNotExistException = () ->
            new BusinessException("CAR NOT EXIST", "Car with given plate number not exists");

    private final CarRepository carRepository;

    public void createCar(CarDto carDto) {
        if (carRepository.findByRegistrationNumber(carDto.getRegistrationNumber()).isPresent()) {
            throw new BusinessException("CAR ALREADY EXIST", "Car with given plate number already exists");
        }
        carRepository.saveCar(carDto);
    }



    public CarDto getCar(String registrationNumber) {
        return carRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(throwCarNotExistException);
    }

    public void deleteCar(String registrationNumber) {
        CarDto carDto = carRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(throwCarNotExistException);
        carRepository.deleteCar(carDto.getRegistrationNumber());
    }

    public void updateCar(CarDto carDto) {
        carRepository.update(carDto);
    }
}
