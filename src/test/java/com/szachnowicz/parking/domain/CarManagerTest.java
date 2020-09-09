package com.szachnowicz.parking.domain;

import com.szachnowicz.parking.dto.CarDto;
import com.szachnowicz.parking.dto.errors.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarManagerTest {
    @Autowired
    CarManager carManager;

    @Test
    void shouldCreateCar() {
        CarDto carDto = new CarDto();
        carDto.setBrand("BMW");
        carDto.setModel("5");
        carDto.setRegistrationNumber("EXIST");
        carManager.createCar(carDto);
        assertEquals(carManager.getCar("EXIST").getModel(), "5");
    }
    @Test
    void shouldThrowExepctionWhileCreateCarWithSameRegistation() {
        CarDto carDto = new CarDto();
        carDto.setModel("BMW");
        carDto.setBrand("5");
        carDto.setRegistrationNumber("ABCD1234");

        BusinessException businessException = assertThrows(BusinessException.class, () -> carManager.createCar(carDto));
        assertEquals(businessException.getErrorCode(), "CAR ALREADY EXIST");
    }




    @Test
    void shouldReturnCar() {
        CarDto car = carManager.getCar("ABCD1234");
    assertEquals(car.getBrand(),"BMW");
    assertEquals(car.getModel(),"3");
    }

    @Test
    void shouldThrowException() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> carManager.getCar("NOT EXIST"));
        assertEquals(businessException.getErrorCode(), "CAR NOT EXIST");
    }


    @Test
    void deleteCar() {
        CarDto carDto = new CarDto();
        carDto.setModel("BMW");
        carDto.setBrand("5");
        carDto.setRegistrationNumber("to delete");

        carManager.createCar(carDto);
        carManager.deleteCar("to delete");
        BusinessException businessException = assertThrows(BusinessException.class, () -> carManager.getCar("to delete"));
        assertEquals(businessException.getErrorCode(), "CAR NOT EXIST");

    }

    @Test
    void updateCar() {
        CarDto carDto = new CarDto();
        carDto.setModel("5");
        carDto.setBrand("BMW");
        carDto.setRegistrationNumber("to update");
        carManager.createCar(carDto);

        carDto.setBrand("7");
        carManager.updateCar(carDto);

        assertEquals(carManager.getCar("to update").getBrand(),"7");



    }
}