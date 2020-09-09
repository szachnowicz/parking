package com.szachnowicz.parking.contoller;

import com.szachnowicz.parking.dto.errors.BusinessException;
import com.szachnowicz.parking.dto.errors.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    public @ResponseBody
    @ExceptionHandler(Exception.class)
    ErrorDTO handleException(Exception exception) {
        log.info(" Exception encountered {}, with message",  exception.getMessage());
        log.debug(" error:", exception);
        return new ErrorDTO(exception.getMessage());
    }
    public @ResponseBody
    @ExceptionHandler(BusinessException.class)
    ErrorDTO handleException(BusinessException exception) {
        log.info("Business Exception encountered {}, with message {}", exception.getErrorCode(), exception.getMessage());
        log.debug("Business error:", exception);
        return new ErrorDTO(exception.getErrorCode(), exception.getMessage());
    }

}