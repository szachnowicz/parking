package com.szachnowicz.parking.dto.errors;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String errorCode;


    public BusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }


}
