package com.szachnowicz.parking.dto.errors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
public class ErrorDTO {
    private String errorCode;
    private String description;

    public ErrorDTO(String errorCode, String message) {
        this.errorCode = errorCode;
        this.description = message;
    }

    public ErrorDTO(String description) {
        this.description = description;
    }
}
