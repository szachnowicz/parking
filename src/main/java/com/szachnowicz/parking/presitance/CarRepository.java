package com.szachnowicz.parking.presitance;

import com.szachnowicz.parking.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarRepository {

    private final CarJpaRepository carJpaRepository;

    public void saveCar(CarDto carDto) {
        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(carDto.getBrand());
        carEntity.setModel(carDto.getModel());
        carEntity.setRegistrationNumber(carDto.getRegistrationNumber());
        carJpaRepository.save(carEntity);
    }

    public Optional<CarDto> findByRegistrationNumber(String registrationNumber) {
        return carJpaRepository.findByRegistrationNumber(registrationNumber).map(CarEntity::toDto);
    }

    @Transactional
    public void deleteCar(String registrationNumber) {
        carJpaRepository.deleteByRegistrationNumber(registrationNumber);
    }

    @Transactional
    public void update(CarDto carDto) {
    carJpaRepository.findByRegistrationNumber(carDto.getRegistrationNumber())
            .ifPresent(carEntity ->{
             carEntity.setModel(carDto.getModel());
             carEntity.setBrand(carDto.getBrand());
                carJpaRepository.save(carEntity);
            });
    }
}

interface CarJpaRepository extends JpaRepository<CarEntity, Long> {
    Optional<CarEntity> findByRegistrationNumber(String registrationNumber);

    void deleteByRegistrationNumber(String registrationNumber);
}
